package com.likelion.miniproject.global.security.jwt;

import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * SecurityContext에 들어가는 인증 주체(principal).
 * @AuthenticationPrincipal AuthUser authUser 형태로 컨트롤러에서 바로 꺼내 쓴다.
 */
public record AuthUser(
        Long userId,
        String username,
        String role
) implements AuthenticatedPrincipal {

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
}
