package com.likelion.miniproject.infrastructure.s3;

import com.likelion.miniproject.global.exception.InfrastructureException;

public class S3UploadException extends InfrastructureException {

    public S3UploadException(Throwable cause) {
        super(S3ErrorCode.S3_UPLOAD_FAILED, S3ErrorCode.S3_UPLOAD_FAILED.getMessage(), cause);
    }
}
