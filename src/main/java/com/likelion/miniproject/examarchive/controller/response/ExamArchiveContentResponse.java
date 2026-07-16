package com.likelion.miniproject.examarchive.controller.response;

import com.likelion.miniproject.examarchive.entity.ExamArchive;

import java.time.LocalDateTime;

public record ExamArchiveContentResponse(
        Long examArchiveId,
        String title,
        String content,
        String writerSemester,
        LocalDateTime createdAt
) {
    public static ExamArchiveContentResponse from(ExamArchive archive, String writerSemester) {
        return new ExamArchiveContentResponse(
                archive.getId(), archive.getTitle(), archive.getContent(), writerSemester, archive.getCreatedAt()
        );
    }
}