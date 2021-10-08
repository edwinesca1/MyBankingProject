package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Account;

public interface AccountDao {
	 
	List<Account> getAllAccounts();
	
	Account getAccountByusername(String username);
	
	void createAccount(Account acc) throws SQLException;
	
	void updateAccountStatus(Account acc) throws SQLException;
}
