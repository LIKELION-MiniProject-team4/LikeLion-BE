package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.BadRequestException;

public class SelfReportNotAllowedException extends BadRequestException {
    public SelfReportNotAllowedException() {
        super(ReviewErrorCode.SELF_REPORT_NOT_ALLOWED);
    }
}