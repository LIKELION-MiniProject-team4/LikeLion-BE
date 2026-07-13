package com.likelion.miniproject.professor.controller.request;

import jakarta.validation.constraints.NotBlank;

public record ProfessorCreateRequest(
        @NotBlank String name,
        @NotBlank String departmentName
) {
}