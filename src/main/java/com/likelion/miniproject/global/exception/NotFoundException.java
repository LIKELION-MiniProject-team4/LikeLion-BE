package com.likelion.miniproject.global.exception;

/** 404 - 리소스를 찾을 수 없음 */
public abstract class NotFoundException extends BusinessException {

    protected NotFoundException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    protected NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected NotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
