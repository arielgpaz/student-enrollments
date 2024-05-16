package com.apaz.studentenrollments.exceptions;


import com.apaz.studentenrollments.domain.responses.CustomError;
import com.apaz.studentenrollments.domain.responses.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class StudentsEnrollmentsExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleExceptions(Exception e) {

        var customError = this.buildCustomError(e, INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.internalServerError().body(customError);
    }

    @ExceptionHandler({ActiveCourseNotFoundException.class, CourseNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<CustomError> handleNotFoundExceptions(RuntimeException e) {

        var customError = this.buildCustomError(e, NOT_FOUND.value());

        return ResponseEntity.status(NOT_FOUND).body(customError);
    }

    @ExceptionHandler({EmailAlreadyInUseException.class, UsernameAlreadyInUseException.class,
            EnrolledStudentException.class, CodeAlreadyInUseException.class})
    public ResponseEntity<CustomError> handleBadRequestExceptions(RuntimeException e) {

        var customError = this.buildCustomError(e, BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleConstraintViolation(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();
        for (var error : ex.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        var validationErrors = new ValidationError("Validation errors",
                                                                        BAD_REQUEST.value(),
                                                                        LocalDateTime.now(),
                                                                        errors);

        return ResponseEntity.badRequest().body(validationErrors);
    }

    private CustomError buildCustomError(Exception e, int status) {
        return CustomError.builder()
                .message(e.getMessage())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
