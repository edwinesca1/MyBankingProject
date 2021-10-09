package com.revature.services;

import java.sql.SQLException;
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
		
		public List<AccountsDisplay> getAllAccountsInfo(int t){
			return accDao.getAllAccountsInfo(t);
		}
		
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
}
