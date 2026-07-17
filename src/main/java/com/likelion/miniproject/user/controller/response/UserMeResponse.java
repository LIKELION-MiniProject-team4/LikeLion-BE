package com.likelion.miniproject.user.controller.response;

import com.likelion.miniproject.user.entity.User;

public record UserMeResponse(
        Long userId,
        String username,
        String name,
        String nickname,
        String phone,
        int point,
        String role
) {
    public static UserMeResponse from(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getPoint(),
                user.getRole().name()
        );
    }
}
