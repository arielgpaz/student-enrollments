package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(

        @NotNull
        Long userId,

        @NotNull
        Long courseId
) {
}
