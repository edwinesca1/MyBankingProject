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
	public void updateAccountStatus(Account acc) throws SQLException {
		
		
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
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
	public void approveDenyAccount(Account acc) throws SQLException {
		
		
		
	}

	@Override
	public void cancelAccount(Account acc) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	
}
