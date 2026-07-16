package com.likelion.miniproject.user.entity;

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

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    // 회원가입 시 지급하는 기본 포인트. PointHistory 엔티티가 생기기 전까지는 직접 세팅한다.
    private static final int SIGNUP_BONUS_POINT = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "point", nullable = false)
    private int point;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private User(String username, String password, String name, String nickname, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.point = SIGNUP_BONUS_POINT;
        this.role = UserRole.USER;
        this.createdAt = LocalDateTime.now();
    }

    public static User create(String username, String password, String name, String nickname, String phone) {
        return new User(username, password, name, nickname, phone);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
