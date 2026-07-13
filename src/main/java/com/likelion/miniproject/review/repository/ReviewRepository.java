package com.likelion.miniproject.review.repository;

import com.likelion.miniproject.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndSubjectId(Long userId, Long subjectId);
    List<Review> findByProfessorIdOrderByCreatedAtAsc(Long professorId);
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
}