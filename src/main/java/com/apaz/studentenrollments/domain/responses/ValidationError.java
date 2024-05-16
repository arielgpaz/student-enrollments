package com.apaz.studentenrollments.domain.responses;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationError extends CustomError {

    private final List<String> errors;

    public ValidationError(String message, Integer status, LocalDateTime timestamp, List<String> errors) {
        super(message, status, timestamp);
        this.errors = errors;
    }

}
