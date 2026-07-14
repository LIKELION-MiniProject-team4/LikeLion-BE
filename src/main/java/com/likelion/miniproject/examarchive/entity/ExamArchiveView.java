package com.likelion.miniproject.examarchive.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_archive_view", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "exam_archive_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamArchiveView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_archive_view_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_archive_id", nullable = false)
    private ExamArchive examArchive;

    @CreationTimestamp
    @Column(name = "viewed_at", nullable = false, updatable = false)
    private LocalDateTime viewedAt;

    @Builder
    public ExamArchiveView(Long userId, ExamArchive examArchive) {
        this.userId = userId;
        this.examArchive = examArchive;
    }
}