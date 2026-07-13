package com.likelion.miniproject.professor.controller.response;

import com.likelion.miniproject.professor.entity.Professor;

public record ProfessorResponse(
        Long professorId,
        String name,
        Long departmentId,
        String departmentName
) {
    public static ProfessorResponse from(Professor professor) {
        return new ProfessorResponse(
                professor.getId(), professor.getName(),
                professor.getDepartment().getId(), professor.getDepartment().getName()
        );
    }
}