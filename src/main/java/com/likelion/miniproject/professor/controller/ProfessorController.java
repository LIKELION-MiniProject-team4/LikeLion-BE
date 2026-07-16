package com.likelion.miniproject.professor.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.professor.controller.request.ProfessorCreateRequest;
import com.likelion.miniproject.professor.controller.response.ProfessorResponse;
import com.likelion.miniproject.professor.controller.response.ProfessorResponseCode;
import com.likelion.miniproject.professor.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<GlobalApiResponse<List<ProfessorResponse>>> getList(
            @RequestParam(required = false) String department
    ) {
        List<ProfessorResponse> result = professorService.getList(department);
        return ResponseEntity.ok(GlobalApiResponse.ok(ProfessorResponseCode.PROFESSOR_LIST_FETCHED, result));
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<GlobalApiResponse<ProfessorResponse>> getDetail(@PathVariable Long professorId) {
        ProfessorResponse result = professorService.getDetail(professorId);
        return ResponseEntity.ok(GlobalApiResponse.ok(ProfessorResponseCode.PROFESSOR_DETAIL_FETCHED, result));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<GlobalApiResponse<ProfessorResponse>> create(@Valid @RequestBody ProfessorCreateRequest request) {
        ProfessorResponse result = professorService.create(request);
        return ResponseEntity.status(201).body(GlobalApiResponse.created(ProfessorResponseCode.PROFESSOR_CREATED, result));
    }
}