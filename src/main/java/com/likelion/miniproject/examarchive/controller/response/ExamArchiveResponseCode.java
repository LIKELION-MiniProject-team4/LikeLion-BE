package com.likelion.miniproject.examarchive.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExamArchiveResponseCode implements ResponseCode {
    EXAM_ARCHIVE_LIST_FETCHED("EXAM_ARCHIVE_200_1", "족보 목록 조회에 성공했습니다."),
    EXAM_ARCHIVE_VIEWED("EXAM_ARCHIVE_200_2", "족보 열람에 성공했습니다.");

    private final String code;
    private final String message;
}
