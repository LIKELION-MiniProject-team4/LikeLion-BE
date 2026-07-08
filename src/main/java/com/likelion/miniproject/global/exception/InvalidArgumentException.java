package com.likelion.miniproject.global.exception;

/** 예시: 어떤 도메인이든 바로 갖다 쓸 수 있는 범용 400 예외 */
public class InvalidArgumentException extends BadRequestException {

    public InvalidArgumentException(String reason) {
        super(CommonErrorCode.INVALID_ARGUMENT);
        addContext("reason", reason);
    }
}
