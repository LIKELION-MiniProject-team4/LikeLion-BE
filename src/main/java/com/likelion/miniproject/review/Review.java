package com.likelion.miniproject.review;

import com.likelion.miniproject.professor.Professor;
import com.likelion.miniproject.subject.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 리뷰. "한 교수의 한 수업당 1개"라는 규칙을 (user_id, subject_id) 유니크로 강제한다.
 * professor_id 는 ERD 원본 컬럼을 유지하되, 실질적으로는 subject.professor 와 항상 같은 값이다.
 */
@Entity
@Table(name = "review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "subject_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Review(Long userId, Professor professor, Subject subject, String content) {
        this.userId = userId;
        this.professor = professor;
        this.subject = subject;
        this.content = content;
    }
}
