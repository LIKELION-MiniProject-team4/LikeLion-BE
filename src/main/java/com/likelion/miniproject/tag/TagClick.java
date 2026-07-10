package com.likelion.miniproject.tag;

import com.likelion.miniproject.professor.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag_click", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "professor_id", "tag_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_click_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public TagClick(Long userId, Professor professor, Tag tag) {
        this.userId = userId;
        this.professor = professor;
        this.tag = tag;
    }
}