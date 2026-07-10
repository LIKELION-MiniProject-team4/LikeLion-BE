package com.likelion.miniproject.user.exception;

import com.likelion.miniproject.global.exception.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {

    public InvalidCredentialsException() {
        super(UserErrorCode.INVALID_CREDENTIALS, UserErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}
