package com.likelion.miniproject.professor;

import jakarta.validation.constraints.NotBlank;

public record ProfessorCreateRequestDto(
        @NotBlank String name,
        @NotBlank String departmentName
) {
}