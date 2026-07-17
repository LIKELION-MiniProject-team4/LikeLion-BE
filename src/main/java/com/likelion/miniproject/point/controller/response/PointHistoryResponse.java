package com.likelion.miniproject.point.controller.response;

import com.likelion.miniproject.point.entity.PointHistory;

import java.time.LocalDateTime;

public record PointHistoryResponse(
        Long pointHistoryId,
        int amount,
        String reason,
        LocalDateTime createdAt
) {
    public static PointHistoryResponse from(PointHistory pointHistory) {
        return new PointHistoryResponse(
                pointHistory.getId(),
                pointHistory.getAmount(),
                pointHistory.getReason().name(),
                pointHistory.getCreatedAt()
        );
    }
}
