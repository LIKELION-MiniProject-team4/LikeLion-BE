package com.likelion.miniproject.global.exception;

import java.util.Map;

/** 예상 가능한 비즈니스 규칙 위반 (중복 요청, 권한 없음, 잘못된 상태 전이 등) */
public abstract class BusinessException extends ApplicationException {

    protected BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    protected BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    protected BusinessException(ErrorCode errorCode, String message, Map<String, Object> context) {
        super(errorCode, message);
        context.forEach(this::addContext);
    }
}
