package com.likelion.miniproject.tag.repository;

import com.likelion.miniproject.tag.entity.TagClick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagClickRepository extends JpaRepository<TagClick, Long> {
    boolean existsByUserIdAndProfessorIdAndTagId(Long userId, Long professorId, Long tagId);
    boolean existsByUserIdAndProfessorId(Long userId, Long professorId);

    @Query("SELECT tc FROM TagClick tc " +
            "JOIN FETCH tc.professor " +
            "JOIN FETCH tc.tag " +
            "WHERE tc.userId = :userId " +
            "ORDER BY tc.createdAt DESC")
    List<TagClick> findByUserIdWithProfessorAndTag(@Param("userId") Long userId);
}