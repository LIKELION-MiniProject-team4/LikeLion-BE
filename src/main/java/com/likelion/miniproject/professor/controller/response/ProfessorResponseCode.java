package com.likelion.miniproject.professor.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfessorResponseCode implements ResponseCode {
    PROFESSOR_CREATED("PROFESSOR_201_1", "교수 등록에 성공했습니다."),
    PROFESSOR_LIST_FETCHED("PROFESSOR_200_1", "교수 목록 조회에 성공했습니다."),
    PROFESSOR_DETAIL_FETCHED("PROFESSOR_200_2", "교수 상세 조회에 성공했습니다.");

    private final String code;
    private final String message;
}