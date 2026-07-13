package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.ConflictException;

public class DuplicateReviewException extends ConflictException {
    public DuplicateReviewException() {
        super(ReviewErrorCode.DUPLICATE_REVIEW);
    }
}