package com.likelion.miniproject.global.exception;

/** 400 - 잘못된 요청 */
public abstract class BadRequestException extends BusinessException {

    protected BadRequestException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    protected BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected BadRequestException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
