package com.likelion.miniproject.point.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointResponseCode implements ResponseCode {
    MY_POINT_HISTORY_FETCHED("POINT_200_1", "내 포인트 내역 조회에 성공했습니다.");

    private final String code;
    private final String message;
}
