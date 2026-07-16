package com.likelion.miniproject.enrollment.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnrollmentResponseCode implements ResponseCode {
    CREATE_SUCCESS("ENROLLMENT_201_1", "수강확인서 등록에 성공했습니다.");

    private final String code;
    private final String message;
}
