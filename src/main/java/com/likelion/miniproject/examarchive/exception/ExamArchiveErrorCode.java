package com.likelion.miniproject.examarchive.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExamArchiveErrorCode implements ErrorCode {
    EXAM_ARCHIVE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXAM_ARCHIVE_404", "존재하지 않는 족보입니다."),
    INSUFFICIENT_POINT(HttpStatus.PAYMENT_REQUIRED, "EXAM_ARCHIVE_402", "포인트가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}