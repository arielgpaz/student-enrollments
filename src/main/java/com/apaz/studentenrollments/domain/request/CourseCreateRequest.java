package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CourseCreateRequest(

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z-]+$")
        String code,

        @NotBlank
        String instructor,

        String description
) {
}
