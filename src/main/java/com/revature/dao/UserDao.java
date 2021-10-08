package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.User;

public interface UserDao {
	
	List<User> getAllUsers();
	
	User getUserByUsername(String userUserName);
	
	void createUser(User u) throws SQLException;
	
}
