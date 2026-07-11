package com.likelion.miniproject.professor;

import com.likelion.miniproject.department.Department;
import com.likelion.miniproject.department.DepartmentRepository;
import com.likelion.miniproject.professor.exception.ProfessorNotFoundException;
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
    public ProfessorResponseDto create(ProfessorCreateRequestDto request) {
        Department department = departmentRepository.findByName(request.departmentName())
                .orElseGet(() -> departmentRepository.save(
                        Department.builder().name(request.departmentName()).build()
                ));

        Professor professor = Professor.builder()
                .name(request.name())
                .department(department)
                .build();
        professorRepository.save(professor);
        return ProfessorResponseDto.from(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDto> getList(String departmentName) {
        return professorRepository.search(departmentName).stream()
                .map(ProfessorResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDto getDetail(Long professorId) {
        return ProfessorResponseDto.from(getProfessorOrThrow(professorId));
    }

    public Professor getProfessorOrThrow(Long professorId) {
        return professorRepository.findById(professorId)
                .orElseThrow(ProfessorNotFoundException::new);
    }
}