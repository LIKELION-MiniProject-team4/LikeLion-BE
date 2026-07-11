package com.likelion.miniproject.professor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDto>> getList(
            @RequestParam(required = false) String department
    ) {
        return ResponseEntity.ok(professorService.getList(department));
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDto> getDetail(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getDetail(professorId));
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDto> create(@Valid @RequestBody ProfessorCreateRequestDto request) {
        return ResponseEntity.status(201).body(professorService.create(request));
    }
}