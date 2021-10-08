package com.revature.exceptions;

public class InvalidCredentialsException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidCredentialsException() {
		super("Credentials does not match");
	}
	
}
