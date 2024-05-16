package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.NotBlank;

public record EnrollmentRequest(

        @NotBlank(message = "O nome do aluno deve ser informado.")
        String username,

        @NotBlank(message = "O código do curso deve ser informado.")
        String courseCode
) {
}
