package com.likelion.miniproject.tag.repository;

import com.likelion.miniproject.tag.entity.TagClick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagClickRepository extends JpaRepository<TagClick, Long> {
    boolean existsByUserIdAndProfessorIdAndTagId(Long userId, Long professorId, Long tagId);
    List<TagClick> findByUserIdOrderByCreatedAtDesc(Long userId);
}