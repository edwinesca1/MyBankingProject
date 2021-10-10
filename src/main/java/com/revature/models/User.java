package com.revature.models;

public class User {
	
	private int userId;
	private String userFirtsName;
	private String userLastName;
	private String userEmail;
	private String userUserName;
	private String userPassword;
	private int userRole;
	
	public User()
	{
		
	}
	
	//Sending info to DB, PK auto generated when sending info to the DB
	public User(String userFirtsName, String userLastName, String userEmail,
			String userUserName, String userPassword) {
		super();
		this.userFirtsName = userFirtsName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userUserName = userUserName;
		this.userPassword = userPassword;
	}

	//Retrieving info from DB
	public User(int userId, String userFirtsName, String userLastName, String userEmail,
			String userUserName, String userPassword, int userRole) {
		super();
		this.userId = userId;
		this.userFirtsName = userFirtsName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userUserName = userUserName;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirtsName() {
		return userFirtsName;
	}

	public void setUserFirtsName(String userFirtsName) {
		this.userFirtsName = userFirtsName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserUserName() {
		return userUserName;
	}

	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userFirtsName=" + userFirtsName + ", userLastName=" + userLastName
				+ ", userEmail=" + userEmail + ", userUserName=" + userUserName
				+ ", userPassword=" + userPassword + "]";
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
}
