package com.likelion.miniproject.enrollment.repository;

import com.likelion.miniproject.enrollment.entity.EnrollmentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentApplicationRepository extends JpaRepository<EnrollmentApplication, Long> {
}
