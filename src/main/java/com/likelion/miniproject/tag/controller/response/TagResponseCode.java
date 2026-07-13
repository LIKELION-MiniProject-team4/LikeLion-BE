package com.likelion.miniproject.tag.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagResponseCode implements ResponseCode {
    TAG_CREATED("TAG_201_1", "태그 등록에 성공했습니다."),
    TAG_LIST_FETCHED("TAG_200_1", "태그 목록 조회에 성공했습니다."),
    TAG_CLICKED("TAG_200_2", "태그 클릭에 성공했습니다.");

    private final String code;
    private final String message;
}