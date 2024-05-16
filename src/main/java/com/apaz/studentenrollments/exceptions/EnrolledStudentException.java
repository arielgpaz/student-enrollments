package com.apaz.studentenrollments.exceptions;

public class EnrolledStudentException extends RuntimeException {

    public EnrolledStudentException(String username, String code) {
        super(String.format("The user '%s' has already been enrolled in course '%s'.", username, code));
    }
}
