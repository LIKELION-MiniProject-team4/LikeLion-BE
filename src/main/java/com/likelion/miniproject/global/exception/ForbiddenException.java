package com.likelion.miniproject.global.exception;

/** 403 - 접근 권한 없음 */
public abstract class ForbiddenException extends BusinessException {

    protected ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected ForbiddenException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
