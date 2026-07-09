package com.likelion.miniproject.user.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Email
        String username,

        @NotBlank @Size(min = 8, max = 100)
        String password,

        @NotBlank
        String name,

        @NotBlank
        String nickname,

        @NotBlank
        String phone
) {
}
