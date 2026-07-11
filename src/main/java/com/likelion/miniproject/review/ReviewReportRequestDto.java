package com.likelion.miniproject.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReportRequestDto {

    @NotBlank
    private String reason;
}