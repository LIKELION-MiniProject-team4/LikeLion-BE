package com.likelion.miniproject.examarchive.repository;

import com.likelion.miniproject.examarchive.entity.ExamArchiveView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamArchiveViewRepository extends JpaRepository<ExamArchiveView, Long> {
    Optional<ExamArchiveView> findByUserIdAndExamArchiveId(Long userId, Long examArchiveId);
}