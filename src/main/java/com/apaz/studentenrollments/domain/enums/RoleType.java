package com.apaz.studentenrollments.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ESTUDANTE("Estudante"),
    INSTRUTOR("Instrutor"),
    ADMIN("Admin");

    private final String value;

}
