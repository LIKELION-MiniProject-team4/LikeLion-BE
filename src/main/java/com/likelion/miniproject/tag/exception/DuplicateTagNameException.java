package com.likelion.miniproject.tag.exception;

import com.likelion.miniproject.global.exception.ConflictException;

public class DuplicateTagNameException extends ConflictException {
    public DuplicateTagNameException() {
        super(TagErrorCode.DUPLICATE_TAG_NAME);
    }
}