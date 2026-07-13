package com.likelion.miniproject.review.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotNull
    private Long subjectId;

    @NotBlank
    private String content;
}