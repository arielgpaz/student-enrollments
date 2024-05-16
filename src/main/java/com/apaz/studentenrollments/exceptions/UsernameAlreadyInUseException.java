package com.apaz.studentenrollments.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {

	public UsernameAlreadyInUseException(String username) {
		super(String.format("This username has already been used: '%s'.", username));
	}
}
