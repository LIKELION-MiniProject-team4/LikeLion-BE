package com.likelion.miniproject.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.miniproject.global.exception.ApplicationException;
import com.likelion.miniproject.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.Map;

@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
public record GlobalApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String traceId,
        Map<String, Object> details
) {
    public static GlobalApiErrorResponse of(ApplicationException exception, String traceId) {
        ErrorCode errorCode = exception.getErrorCode();
        return new GlobalApiErrorResponse(
                LocalDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                exception.getMessage(),
                traceId,
                exception.getContext()
        );
    }

    public static GlobalApiErrorResponse of(ErrorCode errorCode, String traceId) {
        return of(errorCode, traceId, Map.of());
    }

    public static GlobalApiErrorResponse of(ErrorCode errorCode, String traceId, Map<String, Object> details) {
        return new GlobalApiErrorResponse(
                LocalDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                errorCode.getMessage(),
                traceId,
                details
        );
    }
}
