package com.likelion.miniproject.point.entity;

import com.likelion.miniproject.global.point.PointReason;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// User.point가 현재 잔액(source of truth)이고, 이 테이블은 적립/차감 이력을 남기는 감사 로그다.
@Entity
@Table(name = "point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PointReason reason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private PointHistory(Long userId, int amount, PointReason reason) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }

    public static PointHistory create(Long userId, int amount, PointReason reason) {
        return new PointHistory(userId, amount, reason);
    }
}
