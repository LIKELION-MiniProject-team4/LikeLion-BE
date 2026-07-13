package com.likelion.miniproject.tag.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class TagNotFoundException extends NotFoundException {
    public TagNotFoundException() {
        super(TagErrorCode.TAG_NOT_FOUND);
    }
}