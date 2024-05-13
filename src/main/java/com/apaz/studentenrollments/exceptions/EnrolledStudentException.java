package com.apaz.studentenrollments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnrolledStudentException extends RuntimeException {

    public EnrolledStudentException(String username, String code) {
        super(String.format("The user '%s' has already been enrolled in course '%s'.", username, code));
    }
}
