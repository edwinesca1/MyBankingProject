package com.revature.exceptions;

public class UserDoesNotExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException(){
		super("Credentials does not exist");
	}
	
}
