package com.likelion.miniproject.enrollment.controller;

import com.likelion.miniproject.enrollment.controller.response.EnrollmentApplicationResponse;
import com.likelion.miniproject.enrollment.controller.response.EnrollmentResponseCode;
import com.likelion.miniproject.enrollment.service.EnrollmentApplicationService;
import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Enrollment", description = "수강확인서 등록 API")
@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentApplicationService enrollmentApplicationService;

    @Operation(summary = "수강확인서 등록", description = "학기와 수강확인서 파일을 제출한다. 관리자 승인 전까지 PENDING 상태로 유지된다.")
    @PostMapping(value = "/api/enrollments", consumes = "multipart/form-data")
    public ResponseEntity<GlobalApiResponse<EnrollmentApplicationResponse>> create(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam("file") MultipartFile file,
            @RequestParam("semester") String semester
    ) {
        EnrollmentApplicationResponse response = enrollmentApplicationService.create(authUser.userId(), file, semester);
        return ResponseEntity.status(201)
                .body(GlobalApiResponse.created(EnrollmentResponseCode.CREATE_SUCCESS, response));
    }
}
