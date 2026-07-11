package com.likelion.miniproject.professor.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfessorErrorCode implements ErrorCode {
    PROFESSOR_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFESSOR_404", "존재하지 않는 교수입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}