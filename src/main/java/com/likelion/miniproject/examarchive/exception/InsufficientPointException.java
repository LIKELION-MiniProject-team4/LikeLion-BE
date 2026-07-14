package com.likelion.miniproject.examarchive.exception;

import com.likelion.miniproject.global.exception.PaymentRequiredException;

public class InsufficientPointException extends PaymentRequiredException {
    public InsufficientPointException() {
        super(ExamArchiveErrorCode.INSUFFICIENT_POINT);
    }
}