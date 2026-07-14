package com.likelion.miniproject.examarchive.repository;

import com.likelion.miniproject.examarchive.entity.ExamArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamArchiveRepository extends JpaRepository<ExamArchive, Long> {
    List<ExamArchive> findByProfessorIdOrderByCreatedAtDesc(Long professorId);
}