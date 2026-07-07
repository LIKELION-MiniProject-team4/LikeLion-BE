package com.likelion.miniproject.user;

/**
 * 이 모듈이 각 프로젝트의 실제 User 엔티티 모양(닉네임, 전화번호 등)을 몰라도 되게 하는 최소 뷰.
 */
public record AuthUserInfo(
        Long id,
        String username,
        String role
) {
}
