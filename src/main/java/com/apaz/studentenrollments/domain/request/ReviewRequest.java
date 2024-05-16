package com.apaz.studentenrollments.domain.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(

        @NotBlank(message = "O código do curso deve ser informado.")
        String courseCode,

        @NotBlank(message = "O nome do usuário deve ser informado.")
        String username,

        @NotNull(message = "A nota do curso deve ser informada.")
        @Min(value = 0, message = "A nota deve ser maior ou igual a 0.")
        @Max(value = 10, message = "A nota deve ser igual ou menor que 10.")
        Integer rating,

        String description
) {
}
