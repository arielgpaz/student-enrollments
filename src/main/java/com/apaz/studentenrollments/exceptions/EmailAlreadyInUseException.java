package com.apaz.studentenrollments.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {

	public EmailAlreadyInUseException(String email) {
		super(String.format("This e-mail has already been used: '%s'.", email));
	}
}
