package com.likelion.miniproject.professor;

public record ProfessorResponseDto(
        Long professorId,
        String name,
        Long departmentId,
        String departmentName
) {
    public static ProfessorResponseDto from(Professor professor) {
        return new ProfessorResponseDto(
                professor.getId(), professor.getName(),
                professor.getDepartment().getId(), professor.getDepartment().getName()
        );
    }
}