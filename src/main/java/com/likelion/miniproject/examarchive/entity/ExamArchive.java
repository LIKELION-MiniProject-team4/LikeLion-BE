package com.likelion.miniproject.examarchive.entity;

import com.likelion.miniproject.professor.entity.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 족보(수강과목 자료). 텍스트 전용 (파일/이미지 첨부 불가).
 * content(본문)는 열람(포인트 차감) 전까지 응답에서 제외한다.
 */
@Entity
@Table(name = "exam_archive")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_archive_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public ExamArchive(Long userId, Professor professor, String title, String content) {
        this.userId = userId;
        this.professor = professor;
        this.title = title;
        this.content = content;
    }
}