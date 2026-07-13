package com.likelion.miniproject.review.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReportRequest {

    @NotBlank
    private String reason;
}