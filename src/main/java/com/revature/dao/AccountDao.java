package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.models.User;

public interface AccountDao {
	 
	List<AccountsDisplay> getAllAccountsInfo(int t);
	
	List<Account> getAllAccounts();
	
	Account getAccountByusername(String username);
	
	void createAccount(Account acc) throws SQLException;
	
	int updateAccountStatus(String aNum, String aUsername, int newType) throws SQLException;
	
	void approveDenyAccount(Account acc) throws SQLException;
	
	void cancelAccount(Account acc) throws SQLException;
}
