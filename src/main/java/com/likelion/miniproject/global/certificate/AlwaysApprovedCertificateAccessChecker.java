package com.likelion.miniproject.global.certificate;

import org.springframework.stereotype.Component;

/**
 * TODO: certificate 도메인이 완성되면 이 클래스를 삭제하고,
 * CertificateApplicationRepository 등을 사용하는 진짜 구현체로 교체할 것.
 * 지금은 "권한 체크 없이 통과"로 항상 true 를 반환한다.
 */
@Component
public class AlwaysApprovedCertificateAccessChecker implements CertificateAccessChecker {
    @Override
    public boolean isApproved(Long userId, Long professorId) {
        return true;
    }
}