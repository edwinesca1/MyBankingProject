package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.User;

public interface UserDao {
	
	List<User> getAllUsers();
	
	List<User> getAllUsers(int r);
	
	User getUserByUsername(String userUserName);
	
	User createUser(User u) throws SQLException;
	
	int updateUserInfo(int id, String firstName, String lastName, String email, String password) throws SQLException;
	
}
