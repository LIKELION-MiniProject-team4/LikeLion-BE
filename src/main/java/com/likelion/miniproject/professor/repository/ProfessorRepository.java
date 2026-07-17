package com.likelion.miniproject.professor.repository;

import com.likelion.miniproject.professor.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p JOIN FETCH p.department " +
            "WHERE (:department IS NULL OR p.department.name = :department) " +
            "ORDER BY p.name ASC")
    List<Professor> findProfessorsWithDepartment(@Param("department") String department);
}