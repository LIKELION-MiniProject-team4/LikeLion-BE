package com.likelion.miniproject.global.certificate.exception;

import com.likelion.miniproject.global.exception.ForbiddenException;

public class CertificateNotApprovedException extends ForbiddenException {
    public CertificateNotApprovedException() {
        super(CertificateAccessErrorCode.CERTIFICATE_NOT_APPROVED, CertificateAccessErrorCode.CERTIFICATE_NOT_APPROVED.getMessage());
    }
}