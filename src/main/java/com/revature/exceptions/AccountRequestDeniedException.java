package com.revature.exceptions;

public class AccountRequestDeniedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountRequestDeniedException() {
		super("New account request Denied!");
	}	
}
