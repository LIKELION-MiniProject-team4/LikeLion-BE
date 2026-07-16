package com.likelion.miniproject.review.controller.response;

import com.likelion.miniproject.review.entity.Review;

import java.time.LocalDateTime;

public record MyReviewResponse(
        Long reviewId,
        Long professorId,
        String professorName,
        Long subjectId,
        String subjectName,
        String content,
        LocalDateTime createdAt
) {
    public static MyReviewResponse from(Review review) {
        return new MyReviewResponse(
                review.getId(),
                review.getProfessor().getId(),
                review.getProfessor().getName(),
                review.getSubject().getId(),
                review.getSubject().getName(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
