package com.likelion.miniproject.review;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long reviewId,
        Long professorId,
        Long subjectId,
        String subjectName,
        String content,
        LocalDateTime createdAt
) {
    public static ReviewResponseDto from(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getProfessor().getId(),
                review.getSubject().getId(),
                review.getSubject().getName(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}