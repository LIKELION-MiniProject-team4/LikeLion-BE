package com.likelion.miniproject.review.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_404", "존재하지 않는 리뷰입니다."),
    DUPLICATE_REVIEW(HttpStatus.CONFLICT, "REVIEW_409_1", "이미 이 과목에 리뷰를 작성했습니다."),
    DUPLICATE_REVIEW_REPORT(HttpStatus.CONFLICT, "REVIEW_409_2", "이미 신고한 리뷰입니다."),
    SUBJECT_PROFESSOR_MISMATCH(HttpStatus.BAD_REQUEST, "REVIEW_400", "해당 과목은 이 교수의 과목이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}