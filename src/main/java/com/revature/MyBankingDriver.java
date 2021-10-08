package com.revature;

import java.util.Scanner;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.models.User;
import com.revature.services.UserService;

public class MyBankingDriver {
	
	private static UserDao uDao = new UserDaoDB();
	private static UserService uServ = new UserService(uDao);
	
		
	public static void main(String[] args)
	{
		
		Scanner scan = new Scanner(System.in);
		
		//This will be used to control our loop
		boolean done = false;
		
		User uDriver = null;
		
		while(!done) {
			
			if(uDriver == null) {
				System.out.println("My Banking System");
				System.out.println("--> Press 1 to LogIn");
				System.out.println("--> Press 2 to SignUp");
				System.out.print("Option: ");
				int opt = Integer.parseInt(scan.nextLine());
				if(opt == 1) {
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					try {
						uDriver = uServ.signIn(username, password);
						System.out.println("Welcome "+ uDriver.getUserUserName());
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
					
					try {
						uDriver = uServ.signUp(first, last, email, username, password);
						System.out.println("You may now login with the username: " + uDriver.getUserUserName());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Sorry we could not process your request");
						System.out.println("Please try again later...");
					}
				}
				//System.out.println("Would you like to make another transaction?");
				//System.out.print("Press 1 for yes, press 2 to finish your session: ");
			}
			
		}scan.close();
	}
}
