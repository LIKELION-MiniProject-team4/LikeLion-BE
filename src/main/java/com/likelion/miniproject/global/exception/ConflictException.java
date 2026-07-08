package com.likelion.miniproject.global.exception;

/** 409 - 현재 상태와 충돌 (중복 등) */
public abstract class ConflictException extends BusinessException {

    protected ConflictException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    protected ConflictException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected ConflictException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
