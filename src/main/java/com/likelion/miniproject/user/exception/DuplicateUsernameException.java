package com.likelion.miniproject.user.exception;

import com.likelion.miniproject.global.exception.ConflictException;

public class DuplicateUsernameException extends ConflictException {

    public DuplicateUsernameException() {
        super(UserErrorCode.DUPLICATE_USERNAME);
    }
}
