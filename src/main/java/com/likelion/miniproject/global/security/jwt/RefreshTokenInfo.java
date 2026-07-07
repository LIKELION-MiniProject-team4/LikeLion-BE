package com.likelion.miniproject.global.security.jwt;

public record RefreshTokenInfo(
        Long userId,
        String username
) {
}
