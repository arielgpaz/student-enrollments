package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(

        @NotNull
        String courseCode,

        @NotNull
        String username,

        @NotNull
        @Min(0)
        @Max(10)
        Integer rating,

        String description
) {
}
