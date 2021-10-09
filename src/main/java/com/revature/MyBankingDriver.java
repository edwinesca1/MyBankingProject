package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.models.Account;
import com.revature.models.AccountsDisplay;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class MyBankingDriver {
	
	private static UserDao uDao = new UserDaoDB();
	private static UserService uServ = new UserService(uDao);
	private static AccountDao accDao = new AccountDaoDB();
	private static AccountService accServ = new AccountService(accDao);
	
		
	public static void main(String[] args)
	{
		
		Scanner scan = new Scanner(System.in);
		
		//This will be used to control our loop
		boolean done = false;
		
		User uDriver = null;
		Account accDriver = null;
		
		while(!done) {
			
			//Checking if there is a user signed in
			if(uDriver == null) {
				System.out.println("My Banking System");
				System.out.println("--> Press 1 to LogIn");
				System.out.println("--> Press 2 to SignUp");
				System.out.print("Option: ");
				int opt = Integer.parseInt(scan.nextLine());
				
				//User try to sign In if not, then Sign up
				if(opt == 1) {
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println();
					try {
						uDriver = uServ.signIn(username, password);
						System.out.println("Welcome "+ uDriver.getUserUserName());
						System.out.println();
					} catch(Exception e) {
						System.out.println("Invalid Username or Password.");
					}
				}
				else {
					System.out.print("Enter your first name: ");
					String first = scan.nextLine();
					System.out.print("Enter your last name: ");
					String last = scan.nextLine();
					System.out.print("Enter your email: ");
					String email = scan.nextLine();
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println();
					
					try {
						uDriver = uServ.signUp(first, last, email, username, password);
						System.out.println("You may now sign in with the username: " + uDriver.getUserUserName());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Sorry we could not process your request");
						System.out.println("Please try again later...");
					}
				}
			}else {
			
	
			
			//Retrieve Active accounts information and balances
/*			List<AccountsDisplay> acDisplay = accServ.getAllAccountsInfo(1);
			System.out.println("Accounts information List");
			for(AccountsDisplay accounts: acDisplay) {
				System.out.println("User: " + accounts.getUsername());
				System.out.println("Account type: " + accounts.getAccountType());
				System.out.println("Account Number: " + accounts.getAccountNumber());
				System.out.println("Account Balance: " + accounts.getAccountBalance());
				System.out.println();
			}  */
/*			
			//Applying for a new Account
			int newAccountStatus = 0; 
			System.out.println("Select the type of account you want to apply");
			System.out.print("press 1 for Checking account, press 2 for Joint account: ");
			int typeAccount = scan.nextInt();
			System.out.print("Enter the account opening balance: ");
			double startBalance = scan.nextDouble();
			System.out.println();
			
			try {
				accDriver = accServ.createNewAccount(startBalance, newAccountStatus, typeAccount, uDriver.getUserId());
				System.out.println("Processing your request.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Sorry we could not process your request");
				System.out.println("Please try again later...");
			}
	*/		
			//Approving or denying new accounts requests
			//Listing all the pending approval accounts
			List<AccountsDisplay> acDisplay = accServ.getAllAccountsInfo(0);
			System.out.println("Accounts information List");
			System.out.println();
			for(AccountsDisplay accounts: acDisplay) {
				System.out.println("User: " + accounts.getUsername());
				System.out.println("Account type: " + accounts.getAccountType());
				System.out.println("Account Number: " + accounts.getAccountNumber());
				System.out.println("Account Balance: " + accounts.getAccountBalance());
				System.out.println();
			}
			
			System.out.print("Enter the account number: ");
			String accNumber = scan.nextLine();
			System.out.print("Enter the username of the account owner: ");
			String accUsername = scan.nextLine();
			System.out.print("Press 1 to Approve account, press 2 to deny account: ");
			int newStatus = scan.nextInt();
			newStatus = (newStatus == 1) ? 1 : 2;
			scan.nextLine(); // after using nextInt(), next() or other next method leaves a blank jump line, this helps to consume that and avoid troubles.
			System.out.println("Are you sure you want to perform this action?");
			System.out.print("Press 'Y' to continue, press 'N' to cancel: ");
			scan.nextLine();
			String confirm = scan.nextLine();
			
			try {
				int rowsAffected = accServ.updateAccountStatus(accNumber, accUsername, newStatus);
				if(rowsAffected != 0) {
					System.out.println("Update finalized.");
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Sorry we could not process your request");
				System.out.println("Please try again later...");
			}
		  }
		}scan.close();
	}
}
