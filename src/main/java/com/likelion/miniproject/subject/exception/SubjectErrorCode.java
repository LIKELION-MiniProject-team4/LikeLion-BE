package com.likelion.miniproject.subject.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SubjectErrorCode implements ErrorCode {
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBJECT_404", "존재하지 않는 과목입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}