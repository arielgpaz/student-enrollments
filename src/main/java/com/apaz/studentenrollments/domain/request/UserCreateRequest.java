package com.apaz.studentenrollments.domain.request;

import com.apaz.studentenrollments.domain.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(

        @NotBlank(message = "O nome do usuário deve ser informado.")
        String name,

        @NotBlank(message = "O username do usuário deve ser informado.")
        @Pattern(regexp = "^[a-z]+$", message = "Username deve conter apenas caracteres minúsculos, sem numerais e sem espaços.")
        String username,

        @NotBlank(message = "A senha do usuário deve ser informada.")
        String password,

        @NotBlank(message = "O e-mail do usuário deve ser informado.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotNull(message = "O tipo do usuário deve ser informado.")
        RoleType role
) {
}
