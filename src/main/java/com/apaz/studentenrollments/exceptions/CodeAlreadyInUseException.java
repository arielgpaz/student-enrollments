package com.apaz.studentenrollments.exceptions;

public class CodeAlreadyInUseException extends RuntimeException {

	public CodeAlreadyInUseException(String code) {
		super(String.format("This code has already been used: '%s'.", code));
	}
}
