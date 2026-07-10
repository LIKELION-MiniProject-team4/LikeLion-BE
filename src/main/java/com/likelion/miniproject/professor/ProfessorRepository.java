package com.likelion.miniproject.professor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p " +
            "WHERE (:departmentName IS NULL OR p.department.name = :departmentName) " +
            "ORDER BY p.name ASC")
    List<Professor> search(@Param("departmentName") String departmentName);
}