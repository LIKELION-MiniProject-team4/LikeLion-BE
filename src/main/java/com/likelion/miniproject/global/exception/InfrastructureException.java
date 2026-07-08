package com.likelion.miniproject.global.exception;

/** DB 연결 실패, 외부 API 타임아웃 등 시스템/인프라 레벨 문제 (Adapter 계층에서 예외 번역용) */
public abstract class InfrastructureException extends ApplicationException {

    protected InfrastructureException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected InfrastructureException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
