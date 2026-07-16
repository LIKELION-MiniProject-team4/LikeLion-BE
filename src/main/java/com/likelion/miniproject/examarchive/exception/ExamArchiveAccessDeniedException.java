package com.likelion.miniproject.examarchive.exception;

import com.likelion.miniproject.global.exception.ForbiddenException;

public class ExamArchiveAccessDeniedException extends ForbiddenException {
    public ExamArchiveAccessDeniedException() {
        super(ExamArchiveErrorCode.EXAM_ARCHIVE_ACCESS_DENIED, ExamArchiveErrorCode.EXAM_ARCHIVE_ACCESS_DENIED.getMessage());
    }
}