package com.likelion.miniproject.global.certificate;

/**
 * "이 유저가 이 교수의 수강확인서 승인을 받았는가?"를 확인하는 창구.
 * 실제 수강확인서 도메인(certificate)이 완성되면 그쪽에서 이 인터페이스의 진짜 구현체를 만들어 Bean으로 등록한다.
 * 그때까지는 AlwaysApprovedCertificateAccessChecker 로 임시 대체.
 */
public interface CertificateAccessChecker {
    boolean isApproved(Long userId, Long professorId);
}