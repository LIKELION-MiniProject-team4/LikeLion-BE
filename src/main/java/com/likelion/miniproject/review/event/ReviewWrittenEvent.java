package com.likelion.miniproject.review.event;

import java.time.LocalDateTime;
import java.util.Objects;

public record ReviewWrittenEvent(
        Long reviewId,
        Long userId,
        LocalDateTime occurredAt
) {
    public ReviewWrittenEvent {
        Objects.requireNonNull(reviewId, "reviewId는 필수입니다.");
        Objects.requireNonNull(userId, "userId는 필수입니다.");
        Objects.requireNonNull(occurredAt, "occurredAt은 필수입니다.");
    }

    public ReviewWrittenEvent(Long reviewId, Long userId) {
        this(reviewId, userId, LocalDateTime.now());
    }
}
