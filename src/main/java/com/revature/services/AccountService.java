package com.revature.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.exceptions.AccountRequestDeniedException;
import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.logging.Logging;
import com.revature.models.Account;
import com.revature.models.AccountsDisplay;

public class AccountService {

		private AccountDao accDao;
		
		public AccountService(AccountDao acc) {
			this.accDao = acc;
		}
		
		//Method to get accounts info
		public List<AccountsDisplay> getAllAccountsInfo(int t){
			return accDao.getAllAccountsInfo(t);
		}
		
		public List<AccountsDisplay> getSpecificAccount(String username, String accNumber) {
			return accDao.getSpecificAccount(username, accNumber);		
		}
		
		//create a new account 
		public String createNewAccount(double balance, int accStatus, int typeAccount, int userId) {
			
			Account a = new Account(balance, accStatus, typeAccount, userId);
			String accVal = "";
			
			try {
				accVal = accDao.createAccount(a);
				Logging.logger.info("Request for new account completed");
			}catch(SQLException e) {
				Logging.logger.warn("New account request failed!");
				throw new AccountRequestDeniedException();
			}
			
			return accVal;
		}
		
		public int updateAccountStatus(String accNumber, String username, int newStatus) {
			
			int rowsAffectedUpdate = 0;
			
			try {
				rowsAffectedUpdate = accDao.updateAccountStatus(accNumber, username, newStatus);
				Logging.logger.info("Updating account status request completed");
			}catch(SQLException e) {
				Logging.logger.warn("Aproving/Denying request failed");
			}
			
			return rowsAffectedUpdate;
		}
		
		public int withdrawFromAccount(double amount, String username, String accNumber) {
				
			int rowsAffectedWithdraw = 0;
			
			try {
				rowsAffectedWithdraw = accDao.withdrawFromAccount(amount, username, accNumber);
				Logging.logger.info("Withdraw transaction completed");
			}catch(Exception e) {
				Logging.logger.warn("Withdraw money request failed");
			}
			
			return rowsAffectedWithdraw;
		}
		
		public int depositIntoAccount(double amount, String username, String accNumber) {
			
			int rowsAffectedWithdraw = 0;
			
			try {
				rowsAffectedWithdraw = accDao.depositIntoAccount(amount, username, accNumber);
				Logging.logger.info("Deposit transaction completed");
			}catch(Exception e) {
				Logging.logger.warn("Deposit money request failed");
			}
			
			return rowsAffectedWithdraw;
		}
		
		public int transferBetweenAccounts(double amount, String username, String accNumber1, String accNumber2,
				String firstName, String lastName) {
				
			int rowsNumber = 0;
			
			try {
				rowsNumber = accDao.transferBetweenAccounts(amount, username, accNumber1, accNumber2, firstName, lastName);
				Logging.logger.info(" Transfer between accounts transaction completed");
			}catch(SQLException e) {
				Logging.logger.warn("Transfer money request failed");
			}
			
			return rowsNumber;
		}
		
		public int transferBetweenAccounts(double amount, String username1, String username2, String accNumber1, String accNumber2) {
				
			int rowsNumber = 0;
			
			try {
				rowsNumber = accDao.transferBetweenAccounts(amount, username1, username2, accNumber1, accNumber2);
				Logging.logger.info(" Transfer between accounts transaction completed");
			}catch(SQLException e) {
				Logging.logger.warn("Transfer money request failed");
			}
			
			return rowsNumber;
		}
}
