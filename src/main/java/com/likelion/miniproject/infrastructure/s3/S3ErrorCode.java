package com.likelion.miniproject.infrastructure.s3;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3_500_1", "파일 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
