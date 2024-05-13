package com.apaz.studentenrollments.exceptions;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActiveCourseNotFoundException extends RuntimeException {

    public ActiveCourseNotFoundException(@NotNull String code) {
        super(String.format("The course '%s' does not exists or is inactive.", code));
    }
}
