package com.likelion.miniproject.professor.entity;

import com.likelion.miniproject.department.Department;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professor")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Builder
    public Professor(String name, Department department) {
        this.name = name;
        this.department = department;
    }
}