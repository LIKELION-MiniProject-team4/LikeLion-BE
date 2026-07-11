package com.likelion.miniproject.tag.exception;

import com.likelion.miniproject.global.exception.ConflictException;

public class DuplicateTagClickException extends ConflictException {
    public DuplicateTagClickException() {
        super(TagErrorCode.DUPLICATE_TAG_CLICK);
    }
}