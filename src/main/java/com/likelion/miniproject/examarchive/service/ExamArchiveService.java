package com.likelion.miniproject.examarchive.service;

import com.likelion.miniproject.examarchive.controller.response.ExamArchiveContentResponse;
import com.likelion.miniproject.examarchive.controller.response.ExamArchiveListResponse;
import com.likelion.miniproject.examarchive.entity.ExamArchive;
import com.likelion.miniproject.examarchive.entity.ExamArchiveView;
import com.likelion.miniproject.examarchive.exception.ExamArchiveNotFoundException;
import com.likelion.miniproject.examarchive.exception.InsufficientPointException;
import com.likelion.miniproject.examarchive.repository.ExamArchiveRepository;
import com.likelion.miniproject.examarchive.repository.ExamArchiveViewRepository;
import com.likelion.miniproject.global.certificate.CertificateAccessChecker;
import com.likelion.miniproject.global.point.PointReason;
import com.likelion.miniproject.global.point.UserPointManager;
import com.likelion.miniproject.professor.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.miniproject.examarchive.controller.request.ExamArchiveWriteRequest;
import com.likelion.miniproject.examarchive.exception.ExamArchiveAccessDeniedException;
import com.likelion.miniproject.global.certificate.exception.CertificateNotApprovedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamArchiveService {

    private static final int VIEW_POINT_COST = 10;

    private final ExamArchiveRepository examArchiveRepository;
    private final ExamArchiveViewRepository examArchiveViewRepository;
    private final ProfessorService professorService;
    private final CertificateAccessChecker certificateAccessChecker;
    private final UserPointManager userPointManager;

    /** 제목/작성 학기만 노출, 본문은 열람 전까지 비공개 - 비로그인 포함 전체 공개 */
    @Transactional(readOnly = true)
    public List<ExamArchiveListResponse> getList(Long professorId) {
        professorService.getProfessorOrThrow(professorId);
        return examArchiveRepository.findByProfessorIdOrderByCreatedAtDesc(professorId).stream()
                .map(archive -> ExamArchiveListResponse.from(
                        archive,
                        certificateAccessChecker.getApprovedSemester(archive.getUserId(), professorId)
                                .orElse("정보없음")
                ))
                .toList();
    }

    /**
     * 족보 열람.
     * - 최초 열람 시 포인트 -10 차감 (부족하면 402)
     * - 이미 열람한 기록이 있으면 재차감 없이 내용만 반환
     */
    @Transactional
    public ExamArchiveContentResponse view(Long userId, Long examArchiveId) {
        ExamArchive archive = examArchiveRepository.findById(examArchiveId)
                .orElseThrow(ExamArchiveNotFoundException::new);

        boolean alreadyViewed = examArchiveViewRepository
                .findByUserIdAndExamArchiveId(userId, examArchiveId)
                .isPresent();

        if (!alreadyViewed) {
            if (!userPointManager.deduct(userId, VIEW_POINT_COST, PointReason.EXAM_ARCHIVE_VIEW)) {
                throw new InsufficientPointException();
            }
            examArchiveViewRepository.save(ExamArchiveView.builder()
                    .userId(userId)
                    .examArchive(archive)
                    .build());
        }

        String writerSemester = certificateAccessChecker
                .getApprovedSemester(archive.getUserId(), archive.getProfessor().getId())
                .orElse("정보없음");

        return ExamArchiveContentResponse.from(archive, writerSemester);
    }

    /** 텍스트 전용 작성. 수강확인서 승인자만 가능 */
    @Transactional
    public ExamArchiveContentResponse write(Long userId, Long professorId, ExamArchiveWriteRequest request) {
        var professor = professorService.getProfessorOrThrow(professorId);

        if (!certificateAccessChecker.isApproved(userId, professorId)) {
            throw new CertificateNotApprovedException();
        }

        ExamArchive archive = examArchiveRepository.save(ExamArchive.builder()
                .userId(userId)
                .professor(professor)
                .title(request.getTitle())
                .content(request.getContent())
                .build());

        String writerSemester = certificateAccessChecker.getApprovedSemester(userId, professorId).orElse("정보없음");
        return ExamArchiveContentResponse.from(archive, writerSemester);
    }

    /** 본인 또는 관리자만 삭제 가능 */
    @Transactional
    public void delete(Long userId, String role, Long examArchiveId) {
        ExamArchive archive = examArchiveRepository.findById(examArchiveId)
                .orElseThrow(ExamArchiveNotFoundException::new);

        boolean isOwner = archive.getUserId().equals(userId);
        boolean isAdmin = "ADMIN".equals(role);

        if (!isOwner && !isAdmin) {
            throw new ExamArchiveAccessDeniedException();
        }

        examArchiveRepository.delete(archive);
    }


}
