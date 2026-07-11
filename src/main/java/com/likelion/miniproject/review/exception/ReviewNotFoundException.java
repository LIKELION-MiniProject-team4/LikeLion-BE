package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}