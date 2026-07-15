package com.likelion.miniproject.examarchive.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamArchiveWriteRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}