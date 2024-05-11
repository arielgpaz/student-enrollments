package com.apaz.studentregistrations.domain.request;

import com.apaz.studentregistrations.domain.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "^[a-z]+$")
        String username,

        @NotBlank
        String password,

        @NotBlank
        @Email
        String email,

        @NotBlank
        RoleType role
) {
}
