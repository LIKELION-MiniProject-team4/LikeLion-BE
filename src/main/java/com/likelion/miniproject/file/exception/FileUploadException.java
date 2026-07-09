package com.likelion.miniproject.file.exception;

import com.likelion.miniproject.global.exception.InfrastructureException;

// S3 업로드 자체가 실패한 경우 (클라이언트 잘못이 아니라 인프라 문제라서 InfrastructureException 사용)
public class FileUploadException extends InfrastructureException {

    public FileUploadException(Throwable cause) {
        super(FileErrorCode.FILE_UPLOAD_FAILED, FileErrorCode.FILE_UPLOAD_FAILED.getMessage(), cause);
    }
}
