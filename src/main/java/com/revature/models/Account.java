package com.revature.models;

public class Account {
	
	private int AccountId;
	private String accountNumer;
	private double accountBalance;
	private int accountStatus;
	private int accountTypeId;
	private int userId;
	
	public Account()
	{
		
	}
	
	//Sending info to DB
	public Account(String accountNumer, double accountBalance, int accountStatus, int accountTypeId, int userId) {
		super();
		this.accountNumer = accountNumer;
		this.accountBalance = accountBalance;
		this.accountStatus = accountStatus;
		this.accountTypeId = accountTypeId;
		this.userId = userId;
	}
	
	
	//Used to create new accounts
	public Account(double accountBalance, int accountStatus, int accountTypeId, int userId) {
		super();
		this.accountBalance = accountBalance;
		this.accountStatus = accountStatus;
		this.accountTypeId = accountTypeId;
		this.userId = userId;
	}

	//Retriveing info from DB
	public Account(int accountId, String accountNumer, double accountBalance, int accountStatus, int accountTypeId,
			int userId) {
		super();
		AccountId = accountId;
		this.accountNumer = accountNumer;
		this.accountBalance = accountBalance;
		this.accountStatus = accountStatus;
		this.accountTypeId = accountTypeId;
		this.userId = userId;
	}

	public int getAccountId() {
		return AccountId;
	}

	public void setAccountId(int accountId) {
		AccountId = accountId;
	}

	public String getAccountNumer() {
		return accountNumer;
	}

	public void setAccountNumer(String accountNumer) {
		this.accountNumer = accountNumer;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Account [AccountId=" + AccountId + ", accountNumer=" + accountNumer + ", accountBalance="
				+ accountBalance + ", accountStatus=" + accountStatus + ", accountTypeId=" + accountTypeId + ", userId="
				+ userId + "]";
	}

}
