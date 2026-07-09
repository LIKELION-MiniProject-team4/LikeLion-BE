package com.likelion.miniproject.file.exception;

import com.likelion.miniproject.global.exception.BadRequestException;

public class InvalidFileException extends BadRequestException {

    public InvalidFileException(FileErrorCode errorCode) {
        super(errorCode);
    }
}
