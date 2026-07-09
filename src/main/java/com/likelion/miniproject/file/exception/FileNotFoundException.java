package com.likelion.miniproject.file.exception;

import com.likelion.miniproject.global.exception.NotFoundException;

public class FileNotFoundException extends NotFoundException {

    public FileNotFoundException(Long fileId) {
        super(FileErrorCode.FILE_NOT_FOUND, "파일을 찾을 수 없습니다. fileId=" + fileId);
    }
}
