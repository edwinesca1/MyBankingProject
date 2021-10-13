package com.revature.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.models.User;



public class UserServiceTest {
	
	@InjectMocks
	public UserService uServ;
	
	@Mock
	public UserDao uDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidLogin() {
		User u1 = new User(1, "test", "user", "test@mail.com", "testuser", "testpass", 0);
		
		when(uDao.getUserByUsername(anyString())).thenReturn(u1);
		
		User loggedIn = uServ.signIn("testuser", "testpass");
		
		assertEquals(u1.getUserId(), loggedIn.getUserId());
	}
	
	@Test(expected = UserDoesNotExistException.class)
	public void testInvalidUsername() {
		User not = new User(0, "test", "user", "test@mail.com", "testuser", "testpass", 0);
		
		when(uDao.getUserByUsername(anyString())).thenReturn(not);
		
		User loggedIn = uServ.signIn("test", "testpass");
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void testInvalidPassword() {
		User not = new User(1, "test", "user", "test@mail.com", "testuser", "wrongpass",0);
		
		when(uDao.getUserByUsername(anyString())).thenReturn(not);
		
		uServ.signIn("testuser", "testpass");
	}

}
