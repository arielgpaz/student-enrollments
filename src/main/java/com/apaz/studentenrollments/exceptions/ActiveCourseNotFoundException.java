package com.apaz.studentenrollments.exceptions;

import jakarta.validation.constraints.NotNull;

public class ActiveCourseNotFoundException extends RuntimeException {

    public ActiveCourseNotFoundException(@NotNull String code) {
        super(String.format("The course '%s' does not exists or is inactive.", code));
    }
}
