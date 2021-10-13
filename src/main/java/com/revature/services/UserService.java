package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.UserDao;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UserDoesNotExistException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.logging.Logging;
import com.revature.models.User;

public class UserService {
	
	private UserDao uDao;
	
	public UserService(UserDao u) {
		this.uDao = u;
	}
	
	public List<User> getAllUsers(){
		return uDao.getAllUsers();
	}
	
	public List<User> getAllUsers(int r){
		return uDao.getAllUsers(r);
	}
	
	public User signUp(String first, String last, String email, String username, String password) throws UsernameAlreadyExistsException{
		
		User u = new User(first, last, email, username, password);
		User u1 = new User();
		
		try {
			u1 = uDao.createUser(u);
			Logging.logger.info("User successfully created");
		}catch(SQLException e) {
			Logging.logger.warn("User already exists in the data base");
			throw new UsernameAlreadyExistsException();
		}
		
		return u1;
	}
	
	public User signIn(String username, String password) {
		
		User u = uDao.getUserByUsername(username);
		
		if(u.getUserId() == 0) {
			Logging.logger.warn("User does not exist in the data base");
			throw new UserDoesNotExistException();
		}else if(!u.getUserPassword().equals(password)) {
			Logging.logger.warn("User tried to logIn with invalid credentials");
			throw new InvalidCredentialsException();
		}else {
			Logging.logger.info("User logged in successfully");
			return u;
		}		
	}
	
}
