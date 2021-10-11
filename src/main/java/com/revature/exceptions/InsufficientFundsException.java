package com.revature.exceptions;

public class InsufficientFundsException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InsufficientFundsException() {
		super("We couldn't process your transaction, Insufficient funds.");
	}

}
