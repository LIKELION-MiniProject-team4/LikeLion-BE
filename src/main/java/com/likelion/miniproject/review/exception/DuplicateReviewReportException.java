package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.ConflictException;

public class DuplicateReviewReportException extends ConflictException {
    public DuplicateReviewReportException() {
        super(ReviewErrorCode.DUPLICATE_REVIEW_REPORT);
    }
}