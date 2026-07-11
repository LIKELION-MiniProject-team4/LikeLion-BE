package com.likelion.miniproject.global.certificate.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CertificateAccessErrorCode implements ErrorCode {
    CERTIFICATE_NOT_APPROVED(HttpStatus.FORBIDDEN, "CERTIFICATE_403", "수강확인서 승인자만 이용할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
