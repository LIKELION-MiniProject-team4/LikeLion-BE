package com.likelion.miniproject.review.service;

import com.likelion.miniproject.global.certificate.CertificateAccessChecker;
import com.likelion.miniproject.global.certificate.exception.CertificateNotApprovedException;
import com.likelion.miniproject.professor.entity.Professor;
import com.likelion.miniproject.professor.service.ProfessorService;
import com.likelion.miniproject.review.controller.request.ReviewRequest;
import com.likelion.miniproject.review.controller.response.MyReviewResponse;
import com.likelion.miniproject.review.controller.response.ReviewResponse;
import com.likelion.miniproject.review.entity.Review;
import com.likelion.miniproject.review.entity.ReviewReport;
import com.likelion.miniproject.review.exception.DuplicateReviewException;
import com.likelion.miniproject.review.exception.DuplicateReviewReportException;
import com.likelion.miniproject.review.exception.ReviewNotFoundException;
import com.likelion.miniproject.review.exception.SubjectProfessorMismatchException;
import com.likelion.miniproject.review.event.ReviewWrittenEvent;
import com.likelion.miniproject.review.repository.ReviewReportRepository;
import com.likelion.miniproject.review.repository.ReviewRepository;
import com.likelion.miniproject.subject.entity.Subject;
import com.likelion.miniproject.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.miniproject.review.exception.SelfReportNotAllowedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final ProfessorService professorService;
    private final SubjectService subjectService;
    private final CertificateAccessChecker certificateAccessChecker;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(Long professorId) {
        professorService.getProfessorOrThrow(professorId);
        return reviewRepository.findReviewsWithSubjectByProfessorId(professorId).stream()
                .map(review -> ReviewResponse.from(
                        review,
                        certificateAccessChecker.getApprovedSemester(review.getUserId(), professorId)
                                .orElse("정보없음")
                ))
                .toList();
    }

    @Transactional
    public ReviewResponse write(Long userId, Long professorId, ReviewRequest request) {
        Professor professor = professorService.getProfessorOrThrow(professorId);
        Subject subject = subjectService.getSubjectOrThrow(request.getSubjectId());

        if (!subject.getProfessor().getId().equals(professorId)) {
            throw new SubjectProfessorMismatchException();
        }

        if (!certificateAccessChecker.isApproved(userId, professorId)) {
            throw new CertificateNotApprovedException();
        }

        if (reviewRepository.existsByUserIdAndSubjectId(userId, request.getSubjectId())) {
            throw new DuplicateReviewException();
        }

        Review review = reviewRepository.save(Review.builder()
                .userId(userId)
                .professor(professor)
                .subject(subject)
                .content(request.getContent())
                .build());

        eventPublisher.publishEvent(new ReviewWrittenEvent(review.getId(), userId));

        String writerSemester = certificateAccessChecker.getApprovedSemester(userId, professorId).orElse("정보없음");
        return ReviewResponse.from(review, writerSemester);
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponse> getMyReviews(Long userId) {
        return reviewRepository.findByUserIdWithProfessorAndSubject(userId).stream()
                .map(MyReviewResponse::from)
                .toList();
    }

    @Transactional
    public void report(Long reporterId, Long reviewId, String reason) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (review.getUserId().equals(reporterId)) {
            throw new SelfReportNotAllowedException();
        }

        if (reviewReportRepository.existsByReviewIdAndReporterId(reviewId, reporterId)) {
            throw new DuplicateReviewReportException();
        }

        reviewReportRepository.save(ReviewReport.builder()
                .review(review)
                .reporterId(reporterId)
                .reason(reason)
                .build());
    }
}