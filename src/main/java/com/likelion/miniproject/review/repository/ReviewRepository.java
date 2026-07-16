package com.likelion.miniproject.review.repository;

import com.likelion.miniproject.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndSubjectId(Long userId, Long subjectId);
    List<Review> findByProfessorIdOrderByCreatedAtAsc(Long professorId);

    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.professor " +
            "JOIN FETCH r.subject " +
            "WHERE r.userId = :userId " +
            "ORDER BY r.createdAt DESC")
    List<Review> findByUserIdWithProfessorAndSubject(@Param("userId") Long userId);
}