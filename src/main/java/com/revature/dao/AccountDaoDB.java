package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.utils.ConnectionUtil;

public class AccountDaoDB implements AccountDao{
	
	ConnectionUtil conUtil= ConnectionUtil.getConnectionUtil();

	@Override
	public List<Account> getAllAccounts() {
		
			List<Account> accountList = new ArrayList<Account>();
			
			try {
				
				Connection con = conUtil.getConnection();
				String sql = "SELECT * FROM table";
				
				return accountList;
			}
		return null;
	}

	@Override
	public Account getAccountByusername(String username) {
		
		return null;
	}

	@Override
	public void createAccount(Account acc) throws SQLException {
		
		
	}

	@Override
	public void updateAccountStatus(Account acc) throws SQLException {
		
		
	}
	
	
}
