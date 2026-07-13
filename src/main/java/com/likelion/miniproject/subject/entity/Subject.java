package com.likelion.miniproject.subject.entity;

import com.likelion.miniproject.professor.entity.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subject")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Builder
    public Subject(String name, Professor professor) {
        this.name = name;
        this.professor = professor;
    }
}