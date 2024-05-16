package com.apaz.studentenrollments.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String username) {
		super(String.format("User with username '%s' was not found.", username));
	}
}
