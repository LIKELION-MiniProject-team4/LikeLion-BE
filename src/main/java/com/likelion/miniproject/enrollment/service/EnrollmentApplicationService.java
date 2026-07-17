package com.likelion.miniproject.enrollment.service;

import com.likelion.miniproject.enrollment.controller.response.EnrollmentApplicationResponse;
import com.likelion.miniproject.enrollment.entity.EnrollmentApplication;
import com.likelion.miniproject.enrollment.repository.EnrollmentApplicationRepository;
import com.likelion.miniproject.file.service.FileService;
import com.likelion.miniproject.global.exception.InvalidArgumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentApplicationService {

    private final FileService fileService;
    private final EnrollmentApplicationRepository enrollmentApplicationRepository;

    @Transactional
    public EnrollmentApplicationResponse create(Long userId, MultipartFile file, String semester) {
        if (semester == null || semester.isBlank()) {
            throw new InvalidArgumentException("semester는 필수입니다.");
        }

        Long fileId = fileService.upload(file, userId);

        EnrollmentApplication application = EnrollmentApplication.create(userId, semester, fileId);
        enrollmentApplicationRepository.save(application);

        log.info("event=enrollment_application_created enrollmentApplicationId={} userId={}", application.getId(), userId);

        return EnrollmentApplicationResponse.from(application);
    }
}
