package com.revature.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class AccountServiceTest {

	@InjectMocks
	public UserService uServ;
	
	@InjectMocks
	public AccountService aServ;
	
	@Mock
	public UserDao uDao;
	
	@Mock
	public AccountDao aDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateAccount() {
		Account a1 = new Account(1, "2435263", 2000, 1, 1, 1);
		Account a2 = new Account(2, "5378293", 3000, 1, 1, 2);
		Account a3 = new Account(3, "9483642", 1000, 1, 1, 3);	
		
		assertNotNull(a1);
		assertNotNull(a2);
		assertNotNull(a3);
		
	}
	
	@Test
	public void testGetAccounts() {
		Account a4 = new Account(1, "62738294", 2000, 1, 1, 1);
		AccountsDisplay a1 = new AccountsDisplay("Jackmar", "Jackmar", "62738294", 2000);
		AccountsDisplay a2 = new AccountsDisplay("Octoking", "Checking", "3456776", 3000);
		AccountsDisplay a3 = new AccountsDisplay("traveler2", "Checking", "98765433", 4000);
		
		List<AccountsDisplay> accounts = aServ.getSpecificAccount("Jackmar", "62738294");
		
		assertEquals(1, accounts.size());
	}
}
