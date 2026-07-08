package com.likelion.miniproject.global.exception;

/** 401 - 인증 필요 */
public abstract class UnauthorizedException extends BusinessException {

    protected UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected UnauthorizedException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
