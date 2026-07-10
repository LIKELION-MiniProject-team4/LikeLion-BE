package com.likelion.miniproject.tag.exception;

import com.likelion.miniproject.global.exception.ForbiddenException;

public class CertificateNotApprovedException extends ForbiddenException {
    public CertificateNotApprovedException() {
        super(TagErrorCode.CERTIFICATE_NOT_APPROVED, TagErrorCode.CERTIFICATE_NOT_APPROVED.getMessage());
    }
}
