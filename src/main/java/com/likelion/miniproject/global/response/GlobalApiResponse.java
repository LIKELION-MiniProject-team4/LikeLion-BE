package com.likelion.miniproject.global.response;

import org.springframework.http.HttpStatus;

public record GlobalApiResponse<T>(
        int status,
        String code,
        String message,
        T data
) {
    public static <T> GlobalApiResponse<T> of(HttpStatus httpStatus, String code, String message, T data) {
        return new GlobalApiResponse<>(httpStatus.value(), code, message, data);
    }

    public static GlobalApiResponse<Void> of(HttpStatus httpStatus, String code, String message) {
        return new GlobalApiResponse<>(httpStatus.value(), code, message, null);
    }

    public static <T> GlobalApiResponse<T> ok(String code, String message, T data) {
        return of(HttpStatus.OK, code, message, data);
    }

    public static GlobalApiResponse<Void> ok(String code, String message) {
        return of(HttpStatus.OK, code, message);
    }

    public static <T> GlobalApiResponse<T> ok(ResponseCode responseCode, T data) {
        return ok(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static GlobalApiResponse<Void> ok(ResponseCode responseCode) {
        return ok(responseCode.getCode(), responseCode.getMessage());
    }

    public static <T> GlobalApiResponse<T> created(String code, String message, T data) {
        return of(HttpStatus.CREATED, code, message, data);
    }

    public static GlobalApiResponse<Void> created(String code, String message) {
        return of(HttpStatus.CREATED, code, message);
    }

    public static <T> GlobalApiResponse<T> created(ResponseCode responseCode, T data) {
        return created(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static GlobalApiResponse<Void> created(ResponseCode responseCode) {
        return created(responseCode.getCode(), responseCode.getMessage());
    }
}
