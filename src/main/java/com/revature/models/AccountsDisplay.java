package com.revature.models;

public class AccountsDisplay {
	
	private String username;
	private String accountType;
	private String accountNumber;
	private double accountBalance;
	
	public AccountsDisplay() {
		super();
	}

	public AccountsDisplay(String username, String accountType, String accountNumber, double accountBalance) {
		super();
		this.username = username;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "AccountsDisplay [username=" + username + ", accountType=" + accountType + ", accountNumber="
				+ accountNumber + ", accountBalance=" + accountBalance + "]";
	}
	
}
