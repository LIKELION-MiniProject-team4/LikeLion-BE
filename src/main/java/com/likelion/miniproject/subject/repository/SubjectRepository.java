package com.likelion.miniproject.subject.repository;

import com.likelion.miniproject.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}