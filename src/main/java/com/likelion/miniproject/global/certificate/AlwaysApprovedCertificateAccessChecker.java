package com.likelion.miniproject.global.certificate;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * TODO: certificate 도메인이 완성되면 이 클래스를 삭제하고,
 * 실제 승인된 신청서에서 학기값을 꺼내오는 진짜 구현체로 교체할 것.
 * 지금은 "권한 체크 없이 통과"로 항상 true 를 반환하고, 학기는 "정보없음"으로 고정 반환한다.
 */
@Component
public class AlwaysApprovedCertificateAccessChecker implements CertificateAccessChecker {

    @Override
    public boolean isApproved(Long userId, Long professorId) {
        return true;
    }

    @Override
    public Optional<String> getApprovedSemester(Long userId, Long professorId) {
        return Optional.of("정보없음");
    }
}