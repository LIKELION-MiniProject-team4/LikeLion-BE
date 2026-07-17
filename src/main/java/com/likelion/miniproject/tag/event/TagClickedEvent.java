package com.likelion.miniproject.tag.event;

import java.time.LocalDateTime;
import java.util.Objects;

// 한 유저가 한 교수에 대해 "처음" 태그를 클릭했을 때만 발행된다 (교수당 1회 포인트 지급).
public record TagClickedEvent(
        Long tagClickId,
        Long userId,
        Long professorId,
        LocalDateTime occurredAt
) {
    public TagClickedEvent {
        Objects.requireNonNull(tagClickId, "tagClickId는 필수입니다.");
        Objects.requireNonNull(userId, "userId는 필수입니다.");
        Objects.requireNonNull(professorId, "professorId는 필수입니다.");
        Objects.requireNonNull(occurredAt, "occurredAt은 필수입니다.");
    }

    public TagClickedEvent(Long tagClickId, Long userId, Long professorId) {
        this(tagClickId, userId, professorId, LocalDateTime.now());
    }
}
