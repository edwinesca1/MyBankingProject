package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDaoDB implements UserDao{

	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	
	@Override
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		
		try {
			//make the actual connection to the DB
			Connection con = conUtil.getConnection();
			
			//Creating a simple statement
			String sql = "SELECT * FROM table_user";
			
			//We need to create a statement with the sql string
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			//We have to loop through the resultset an create objects based off the return
			while(rs.next()) {
				userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			return userList;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	public User getUserByUsername(String userUserName) {
		
		User user = new User();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT * FROM table_user WHERE table_user.user_username = '" + userUserName + "'";
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setUserFirtsName(rs.getString(2));
				user.setUserLastName(rs.getString(3));
				user.setUserEmail(rs.getString(4));
				user.setUserUserName(rs.getString(5));
				user.setUserPassword(rs.getString(6));
			}
			return user;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void createUser(User u) throws SQLException {
		
		Connection con = conUtil.getConnection();
		
		//Another way to crate the statement for the query
		String sql = "INSERT INTO table_user(user_first_name, user_last_name, user_email, user_username, user_password) VALUES"
				+"(?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getUserFirtsName());
		ps.setString(2, u.getUserLastName());
		ps.setString(3, u.getUserEmail());
		ps.setString(4, u.getUserUserName());
		ps.setString(5, u.getUserPassword());
		
		ps.execute();
		
	}

	
}
