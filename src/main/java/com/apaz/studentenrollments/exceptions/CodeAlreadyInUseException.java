package com.apaz.studentenrollments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CodeAlreadyInUseException extends RuntimeException {

	public CodeAlreadyInUseException(String code) {
		super(String.format("This code has already been used: '%s'.", code));
	}
}
