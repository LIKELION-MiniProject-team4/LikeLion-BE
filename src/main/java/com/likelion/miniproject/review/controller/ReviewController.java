package com.likelion.miniproject.review.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import com.likelion.miniproject.review.controller.request.ReviewReportRequest;
import com.likelion.miniproject.review.controller.request.ReviewRequest;
import com.likelion.miniproject.review.controller.response.ReviewResponse;
import com.likelion.miniproject.review.controller.response.ReviewResponseCode;
import com.likelion.miniproject.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/professors/{professorId}/reviews")
    public ResponseEntity<GlobalApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long professorId) {
        List<ReviewResponse> result = reviewService.getReviews(professorId);
        return ResponseEntity.ok(GlobalApiResponse.ok(ReviewResponseCode.REVIEW_LIST_FETCHED, result));
    }

    @PostMapping("/api/professors/{professorId}/reviews")
    public ResponseEntity<GlobalApiResponse<ReviewResponse>> writeReview(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long professorId,
            @Valid @RequestBody ReviewRequest request
    ) {
        ReviewResponse result = reviewService.write(authUser.userId(), professorId, request);
        return ResponseEntity.status(201).body(GlobalApiResponse.created(ReviewResponseCode.REVIEW_CREATED, result));
    }

    @PostMapping("/api/reviews/{reviewId}/reports")
    public ResponseEntity<GlobalApiResponse<Void>> reportReview(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewReportRequest request
    ) {
        reviewService.report(authUser.userId(), reviewId, request.getReason());
        return ResponseEntity.ok(GlobalApiResponse.ok(ReviewResponseCode.REVIEW_REPORTED));
    }
}