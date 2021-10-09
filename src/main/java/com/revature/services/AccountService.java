package com.revature.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.exceptions.AccountRequestDeniedException;
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
		
		//create a new account 
		public Account createNewAccount(double balance, int accStatus, int typeAccount, int userId) {
			
			Account a = new Account(balance, accStatus, typeAccount, userId);
			
			try {
				accDao.createAccount(a);
				Logging.logger.info("Request for new account completed");
			}catch(SQLException e) {
				Logging.logger.warn("Request denied!");
				throw new AccountRequestDeniedException();
			}
			
			return a;
		}
		
		public int updateAccountStatus(String accNumber, String username, int newStatus) {
			
			int rowsaffectedUpdate = 0;
			
			try {
				rowsaffectedUpdate = accDao.updateAccountStatus(accNumber, username, newStatus);
				Logging.logger.info("Updating account status request completed");
			}catch(SQLException e) {
				Logging.logger.warn("Request failed");
			}
			
			return rowsaffectedUpdate;
		}
}
