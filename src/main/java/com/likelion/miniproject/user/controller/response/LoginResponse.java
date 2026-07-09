package com.likelion.miniproject.user.controller.response;

public record LoginResponse(
        String accessToken,
        String role
) {
}
