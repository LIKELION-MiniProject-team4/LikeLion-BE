package com.likelion.miniproject.examarchive.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class ExamArchiveNotFoundException extends NotFoundException {
    public ExamArchiveNotFoundException() {
        super(ExamArchiveErrorCode.EXAM_ARCHIVE_NOT_FOUND);
    }
}