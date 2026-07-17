package com.likelion.miniproject.user.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
