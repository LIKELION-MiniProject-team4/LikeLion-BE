package com.likelion.miniproject.user.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "token", nullable = false, length = 512)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private RefreshToken(Long userId, String token, LocalDateTime createdAt) {
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
    }

    public static RefreshToken create(Long userId, String token) {
        return new RefreshToken(userId, token, LocalDateTime.now());
    }

    public void rotate(String newToken) {
        this.token = newToken;
        this.createdAt = LocalDateTime.now();
    }
}
