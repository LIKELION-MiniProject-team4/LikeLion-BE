package com.likelion.miniproject.examarchive.controller.response;

import com.likelion.miniproject.examarchive.entity.ExamArchive;

import java.time.LocalDateTime;

public record ExamArchiveListResponse(
        Long examArchiveId,
        String title,
        String writerSemester,
        LocalDateTime createdAt
) {
    public static ExamArchiveListResponse from(ExamArchive archive, String writerSemester) {
        return new ExamArchiveListResponse(
                archive.getId(), archive.getTitle(), writerSemester, archive.getCreatedAt()
        );
    }
}