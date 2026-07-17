package com.likelion.miniproject.tag.controller.response;

import com.likelion.miniproject.tag.entity.TagClick;

import java.time.LocalDateTime;

public record MyTagClickResponse(
        Long tagClickId,
        Long professorId,
        String professorName,
        Long tagId,
        String tagName,
        LocalDateTime createdAt
) {
    public static MyTagClickResponse from(TagClick tagClick) {
        return new MyTagClickResponse(
                tagClick.getId(),
                tagClick.getProfessor().getId(),
                tagClick.getProfessor().getName(),
                tagClick.getTag().getId(),
                tagClick.getTag().getName(),
                tagClick.getCreatedAt()
        );
    }
}
