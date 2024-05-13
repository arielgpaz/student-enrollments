package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(

        @NotNull
        String username,

        @NotNull
        String courseCode
) {
}
