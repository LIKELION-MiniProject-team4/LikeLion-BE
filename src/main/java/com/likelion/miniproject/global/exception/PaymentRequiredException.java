package com.likelion.miniproject.global.exception;

/** 402 - 결제 필요 (포인트 부족 등) */
public abstract class PaymentRequiredException extends BusinessException {

    protected PaymentRequiredException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    protected PaymentRequiredException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected PaymentRequiredException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}