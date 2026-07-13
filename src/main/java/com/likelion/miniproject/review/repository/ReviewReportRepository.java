package com.likelion.miniproject.review.repository;

import com.likelion.miniproject.review.entity.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {
    boolean existsByReviewIdAndReporterId(Long reviewId, Long reporterId);
}