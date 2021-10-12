package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.models.User;

public interface AccountDao {
	 
	List<AccountsDisplay> getAllAccountsInfo(int t);
	
	List<AccountsDisplay> getSpecificAccount(String username, String accNumber);
	
	Account getAccountByusername(String username);
	
	void createAccount(Account acc) throws SQLException;
	
	int updateAccountStatus(String aNum, String aUsername, int newType) throws SQLException;
	
	void cancelAccount(Account acc) throws SQLException;
	
	int withdrawFromAccount(double amount, String username, String accNumber) throws SQLException;
	
	int depositIntoAccount(double amount, String username, String accNumber) throws SQLException;
	
	int transferBetweenAccounts (double amount, String username, String accNumber1, String accNumber2, String firstName, String lastName) throws SQLException;
}
