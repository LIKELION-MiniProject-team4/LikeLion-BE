package com.likelion.miniproject.user.controller.response;

import com.likelion.miniproject.global.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode implements ResponseCode {
    SIGNUP_SUCCESS("USER_201_1", "회원가입에 성공했습니다."),
    LOGIN_SUCCESS("USER_200_1", "로그인에 성공했습니다."),
    LOGOUT_SUCCESS("USER_200_2", "로그아웃에 성공했습니다.");

    private final String code;
    private final String message;
}
