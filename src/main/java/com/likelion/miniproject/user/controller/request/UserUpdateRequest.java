package com.likelion.miniproject.user.controller.request;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 1)
        String name,

        @Size(min = 1)
        String nickname,

        @Size(min = 8, max = 100)
        String newPassword
) {
}
