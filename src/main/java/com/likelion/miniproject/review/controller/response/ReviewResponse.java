package com.likelion.miniproject.review.controller.response;

import com.likelion.miniproject.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long reviewId,
        Long professorId,
        Long subjectId,
        String subjectName,
        String content,
        String writerSemester,
        LocalDateTime createdAt
) {
    public static ReviewResponse from(Review review, String writerSemester) {
        return new ReviewResponse(
                review.getId(),
                review.getProfessor().getId(),
                review.getSubject().getId(),
                review.getSubject().getName(),
                review.getContent(),
                writerSemester,
                review.getCreatedAt()
        );
    }
}