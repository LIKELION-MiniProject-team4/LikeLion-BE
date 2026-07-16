package com.likelion.miniproject.enrollment.entity;

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

// 수강확인서 제출 단위 (파일 하나 = 1 row). 관리자가 파일을 보고 승인하면서
// 그 안에 포함된 과목 개수만큼 Enrollment row를 생성한다 (승인 플로우는 별도 기능에서 구현).
@Entity
@Table(name = "enrollment_application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnrollmentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_application_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String semester;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentApplicationStatus status;

    @Column(name = "reject_reason", length = 500)
    private String rejectReason;

    @Column(name = "reviewed_by")
    private Long reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private EnrollmentApplication(Long userId, String semester, Long fileId) {
        this.userId = userId;
        this.semester = semester;
        this.fileId = fileId;
        this.status = EnrollmentApplicationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public static EnrollmentApplication create(Long userId, String semester, Long fileId) {
        return new EnrollmentApplication(userId, semester, fileId);
    }
}
