package com.likelion.miniproject.examarchive.controller;

import com.likelion.miniproject.examarchive.controller.response.ExamArchiveContentResponse;
import com.likelion.miniproject.examarchive.controller.response.ExamArchiveListResponse;
import com.likelion.miniproject.examarchive.controller.response.ExamArchiveResponseCode;
import com.likelion.miniproject.examarchive.service.ExamArchiveService;
import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamArchiveController {

    private final ExamArchiveService examArchiveService;

    /** 비로그인 포함 전체 공개 */
    @GetMapping("/api/professors/{professorId}/exam-archives")
    public ResponseEntity<GlobalApiResponse<List<ExamArchiveListResponse>>> getList(@PathVariable Long professorId) {
        List<ExamArchiveListResponse> result = examArchiveService.getList(professorId);
        return ResponseEntity.ok(GlobalApiResponse.ok(ExamArchiveResponseCode.EXAM_ARCHIVE_LIST_FETCHED, result));
    }

    /** 로그인 필요. 열람 시 포인트 -10 차감 (재열람 시 재차감 없음) */
    @GetMapping("/api/exam-archives/{examArchiveId}/content")
    public ResponseEntity<GlobalApiResponse<ExamArchiveContentResponse>> view(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long examArchiveId
    ) {
        ExamArchiveContentResponse result = examArchiveService.view(authUser.userId(), examArchiveId);
        return ResponseEntity.ok(GlobalApiResponse.ok(ExamArchiveResponseCode.EXAM_ARCHIVE_VIEWED, result));
    }
}