package com.likelion.miniproject.review.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewResponseCode implements ResponseCode {
    REVIEW_CREATED("REVIEW_201_1", "리뷰 작성에 성공했습니다."),
    REVIEW_LIST_FETCHED("REVIEW_200_1", "리뷰 목록 조회에 성공했습니다."),
    REVIEW_REPORTED("REVIEW_200_2", "리뷰 신고에 성공했습니다.");

    private final String code;
    private final String message;
}