package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.BadRequestException;

public class SubjectProfessorMismatchException extends BadRequestException {
    public SubjectProfessorMismatchException() {
        super(ReviewErrorCode.SUBJECT_PROFESSOR_MISMATCH);
    }
}