package com.likelion.miniproject.professor.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class ProfessorNotFoundException extends NotFoundException {
    public ProfessorNotFoundException() {
        super(ProfessorErrorCode.PROFESSOR_NOT_FOUND);
    }
}