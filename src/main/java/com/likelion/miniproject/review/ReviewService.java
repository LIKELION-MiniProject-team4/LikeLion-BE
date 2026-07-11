package com.likelion.miniproject.review;

import com.likelion.miniproject.global.certificate.CertificateAccessChecker;
import com.likelion.miniproject.global.certificate.exception.CertificateNotApprovedException;
import com.likelion.miniproject.professor.Professor;
import com.likelion.miniproject.professor.ProfessorService;
import com.likelion.miniproject.review.exception.DuplicateReviewException;
import com.likelion.miniproject.review.exception.DuplicateReviewReportException;
import com.likelion.miniproject.review.exception.ReviewNotFoundException;
import com.likelion.miniproject.review.exception.SubjectProfessorMismatchException;
import com.likelion.miniproject.subject.Subject;
import com.likelion.miniproject.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final ProfessorService professorService;
    private final SubjectService subjectService;
    private final CertificateAccessChecker certificateAccessChecker;

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(Long professorId) {
        professorService.getProfessorOrThrow(professorId);
        return reviewRepository.findByProfessorIdOrderByCreatedAtAsc(professorId).stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    @Transactional
    public ReviewResponseDto write(Long userId, Long professorId, ReviewRequestDto request) {
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

        return ReviewResponseDto.from(review);
    }

    @Transactional
    public void report(Long reporterId, Long reviewId, String reason) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

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