package com.likelion.miniproject.tag;

import jakarta.validation.constraints.NotBlank;

public record TagCreateRequestDto(
        @NotBlank String name
) {
}