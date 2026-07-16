package com.likelion.miniproject.enrollment.controller.response;

import com.likelion.miniproject.enrollment.entity.EnrollmentApplication;

public record EnrollmentApplicationResponse(
        Long enrollmentApplicationId,
        String semester,
        String status
) {
    public static EnrollmentApplicationResponse from(EnrollmentApplication application) {
        return new EnrollmentApplicationResponse(
                application.getId(),
                application.getSemester(),
                application.getStatus().name()
        );
    }
}
