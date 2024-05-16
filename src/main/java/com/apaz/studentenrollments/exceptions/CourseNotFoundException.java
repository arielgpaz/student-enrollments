package com.apaz.studentenrollments.exceptions;

public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException(String code) {
		super(String.format("Course with code '%s' was not found.", code));
	}
}
