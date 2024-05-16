package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CourseCreateRequest(

        @NotBlank(message = "O nome do curso deve ser informado.")
        String name,

        @NotBlank(message = "O código do usuário deve ser informado.")
        @Pattern(regexp = "^[a-zA-Z-]+$", message = "O código do curso deve ser textual, sem espaços," +
                " sem caracteres numéricos e sem caracteres especiais, mas pode ser separado por - .")
        String code,

        @NotBlank(message = "O username do instrutor deve ser informado.")
        String instructorUsername,

        String description
) {
}
