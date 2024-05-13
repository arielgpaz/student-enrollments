package com.apaz.studentenrollments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException(String code) {
		super(String.format("Course with code '%s' was not found.", code));
	}
}
