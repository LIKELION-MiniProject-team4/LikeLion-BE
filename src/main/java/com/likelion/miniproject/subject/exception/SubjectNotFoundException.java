package com.likelion.miniproject.subject.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class SubjectNotFoundException extends NotFoundException {
    public SubjectNotFoundException() {
        super(SubjectErrorCode.SUBJECT_NOT_FOUND);
    }
}