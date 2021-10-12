package com.revature.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class AccountDaoDB implements AccountDao{
	
	ConnectionUtil conUtil= ConnectionUtil.getConnectionUtil();


	@Override
	public Account getAccountByusername(String username) {
		
		return null;
	}

	@Override
	public void createAccount(Account acc) throws SQLException {
		
		Connection con = conUtil.getConnection();
		
		//Another way to crate the statement for the query
		String sql = "insert into table_account(account_balance, account_status, account_type_id, account_user_id) values"
				+"(?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setDouble(1, acc.getAccountBalance());
		ps.setInt(2, acc.getAccountStatus());
		ps.setInt(3, acc.getAccountTypeId());
		ps.setInt(4, acc.getUserId());
		
		ps.execute();
		
	}

	@Override
	public int updateAccountStatus(String aNum, String aUsername, int newType) throws SQLException {
		
		Connection con = conUtil.getConnection();
		
		String sql = "update table_account "
				+ "set	account_status = "+ newType
				+ " where account_number = '"+ aNum
				+ "' and account_user_id = (select u.user_id from table_user u where u.user_username = '"+ aUsername +"')";
		
		Statement s = con.createStatement();
		int rowsAffected = s.executeUpdate(sql);
		
		return rowsAffected;
	}
	

	@Override
	public List<AccountsDisplay> getAllAccountsInfo(int t) {
		
		List<AccountsDisplay> accountList = new ArrayList<AccountsDisplay>();
		
		try {
			
			Connection con = conUtil.getConnection();
			
			String sql = "select u.user_username as username,"
					+ "tat.account_type_name as account_type,"
					+ "ac.account_number,"
					+ "ac.account_balance as balance "
					+ "from   table_user u "
					+ "inner join table_account ac"
					+ "		on u.user_id = ac.account_user_id "
					+ "inner join table_account_types tat"
					+ "		on ac.account_type_id = tat.account_type_id where ac.account_status = " + t
					+ "group by u.user_username,"
					+ "		 tat.account_type_name,"
					+ "		 ac.account_number,"
					+ "		 ac.account_balance";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				accountList.add(new AccountsDisplay(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
			}
			return accountList;
		}catch(SQLException e) {
			e.printStackTrace();
		}
	return null;
	}

	@Override
	public void cancelAccount(Account acc) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AccountsDisplay> getSpecificAccount(String username, String accNumber) {
		
		List<AccountsDisplay> accountInfo = new ArrayList<AccountsDisplay>();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "select u.user_username as username,"
					+ "tat.account_type_name as account_type,"
					+ "ac.account_number,"
					+ "ac.account_balance as balance "
					+ "from   table_user u "
					+ "inner join table_account ac"
					+ "		on u.user_id = ac.account_user_id "
					+ "inner join table_account_types tat"
					+ "		on ac.account_type_id = tat.account_type_id "
					+ "where ac.account_number = '"+ accNumber +"'"
					+ " and ac.account_user_id = (select u.user_id from table_user u where u.user_username = '"+ username +"') "
					+ "group by u.user_username,"
					+ "		 tat.account_type_name,"
					+ "		 ac.account_number,"
					+ "		 ac.account_balance";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
					accountInfo.add(new AccountsDisplay(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return accountInfo;
	}

	@Override
	public int withdrawFromAccount(double amount, String username, String accNumber) throws SQLException{
		
		Connection con = conUtil.getConnection();
		
		String sql = "update table_account "
					+ "set	account_balance = (account_balance - "+ amount +")"
					+ "where account_status = 1 and account_balance >= "+ amount +" and account_number = '"+accNumber+"' "
					+ "and account_user_id = (select u.user_id from table_user u where u.user_username = '"+ username +"')";
		
		Statement s = con.createStatement();
		int rowsAffected = s.executeUpdate(sql);

		return rowsAffected;
	}

	@Override
	public int depositIntoAccount(double amount, String username, String accNumber) throws SQLException {
		
		Connection con = conUtil.getConnection();
		
		String sql = "update table_account "
					+ "set	account_balance = (account_balance + "+ amount +")"
					+ "where account_status = 1 and account_number = '"+accNumber+"' "
					+ "and account_user_id = (select u.user_id from table_user u where u.user_username = '"+ username +"')";
		
		Statement s = con.createStatement();
		int rowsAffected = s.executeUpdate(sql);
		
		return rowsAffected;
	}

	@Override
	public int transferBetweenAccounts(double amount, String username, String accNumber1, String accNumber2,
			String firstName, String lastName) throws SQLException{
		
			Connection con = conUtil.getConnection();
			
			int rowsAffected = 0;
			
			String sql1 = "select * from table_account "
					+ "where account_status = 1 and account_number = '"+ accNumber1 +"' and account_balance >= "+ amount +" "
					+ "and account_user_id = (select u.user_id from table_user u where u.user_username = '"+ username +"')";
			
			String sql2 = "select * from table_account ta inner join table_user tu on tu.user_id = ta.account_user_id "
					+ "where ta.account_status = 1 and ta.account_number = '"+ accNumber2 +"' "
					+ "and tu.user_first_name = '"+ firstName +"' and tu.user_last_name = '"+ lastName +"'";
			
			Statement s1 = con.createStatement();
			ResultSet rs1 = s1.executeQuery(sql1);
			
			Statement s2 = con.createStatement();
			ResultSet rs2 =s2.executeQuery(sql2);
			
			if(rs1.next() && rs2.next()) {
				
				int rows1 = withdrawFromAccount(amount, username, accNumber1);
				int rows2 = depositIntoAccount(amount, rs2.getString(11), accNumber2);
				
				if(rows1 != 0 && rows2 != 0) {
					rowsAffected = 1;
				}
			}
			
			
		return rowsAffected;
	}
	
	
}
