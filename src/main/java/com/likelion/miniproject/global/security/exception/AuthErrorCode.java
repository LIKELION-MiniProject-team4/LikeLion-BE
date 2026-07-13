package com.likelion.miniproject.global.security.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ACCESS_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "AUTH_401_001", "Access Token이 없습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_401_002", "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_003", "유효하지 않은 Access Token입니다."),
    INVALID_TOKEN_SUBJECT(HttpStatus.UNAUTHORIZED, "AUTH_401_004", "토큰 subject가 유효하지 않습니다."),
    USERNAME_CLAIM_MISSING(HttpStatus.UNAUTHORIZED, "AUTH_401_005", "username claim이 없습니다."),
    ROLE_CLAIM_MISSING(HttpStatus.UNAUTHORIZED, "AUTH_401_006", "role claim이 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH_403_001", "접근 권한이 없습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_401_007", "RefreshToken을 찾을 수 없습니다. 다시 로그인해주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_008", "유효하지 않은 RefreshToken입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "AUTH_401_009", "저장된 RefreshToken과 일치하지 않습니다. 재사용/탈취가 의심됩니다."),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_401_010", "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
