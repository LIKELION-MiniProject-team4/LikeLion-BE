package com.likelion.miniproject.professor.service;

import com.likelion.miniproject.department.Department;
import com.likelion.miniproject.department.DepartmentRepository;
import com.likelion.miniproject.professor.controller.request.ProfessorCreateRequest;
import com.likelion.miniproject.professor.controller.response.ProfessorResponse;
import com.likelion.miniproject.professor.entity.Professor;
import com.likelion.miniproject.professor.exception.ProfessorNotFoundException;
import com.likelion.miniproject.professor.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public ProfessorResponse create(ProfessorCreateRequest request) {
        Department department = departmentRepository.findByName(request.departmentName())
                .orElseGet(() -> departmentRepository.save(
                        Department.builder().name(request.departmentName()).build()
                ));

        Professor professor = Professor.builder()
                .name(request.name())
                .department(department)
                .build();
        professorRepository.save(professor);
        return ProfessorResponse.from(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> getList(String departmentName) {
        return professorRepository.search(departmentName).stream()
                .map(ProfessorResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponse getDetail(Long professorId) {
        return ProfessorResponse.from(getProfessorOrThrow(professorId));
    }

    public Professor getProfessorOrThrow(Long professorId) {
        return professorRepository.findById(professorId)
                .orElseThrow(ProfessorNotFoundException::new);
    }
}