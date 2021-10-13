package com.revature;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		String regex = "[+-]?[0-9]+";

	      // Create a Pattern object
		Pattern p = Pattern.compile(regex);
		
		while(!done) {
			
			//Checking if there is a user signed in
			if(uDriver == null) {
				System.out.println();
				System.out.println("My Banking System");
				System.out.println("--> Press 1 to LogIn");
				System.out.println("--> Press 2 to SignUp");
				System.out.println("To exit press number 9");
				System.out.print("Option: ");
				int opt = 0;
				String val = scan.nextLine();
				System.out.println();
				if(val.equals("1") || val.equals("2") || val.equals("9")) {
					opt = Integer.parseInt(val);	
				}else {
					System.out.print("Enter a valid option.");
					System.out.println();
				}
				
				//User try to sign In if not, then Sign up
				if(opt == 1) {
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println();
					try {
						uDriver = uServ.signIn(username, password);
						System.out.println("---Welcome "+ uDriver.getUserUserName() + "---");
					} catch(Exception e) {
						System.out.println("Invalid Username or Password.");
					}
				}
				else if(opt == 2){
					System.out.print("Enter your first name: ");
					String first = scan.nextLine();
					System.out.print("Enter your last name: ");
					String last = scan.nextLine();
					System.out.print("Enter your email: ");
					String email = scan.nextLine();
					System.out.print("Enter your username: ");
					String username = scan.nextLine();
					username = username.replaceAll("[^a-zA-Z0-9\\s]", "");
					System.out.print("Enter your password: ");
					String password = scan.nextLine();
					System.out.println();
					
					try {
						uDriver = uServ.signUp(first, last, email, username, password);
						System.out.println("You may now sign in with the username: " + uDriver.getUserUserName());
						System.out.println("Your user ID: " + uDriver.getUserId());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Sorry we could not process your request");
						System.out.println("Please try again later...");
					}
				}else if(opt == 9) {
					done = true;
				}
				
			}else {
				
				//Role 0 means user is a customer
				if(uDriver.getUserRole() == 0) {
					System.out.println();
					System.out.println("What would you like to do?");
					System.out.println("1) Withdraw money");
					System.out.println("2) Deposit money");
					System.out.println("3) Transfer money");
					System.out.println("4) Open an account");
				  //System.out.println("5) Apply for a joint account");
					System.out.println("To exit press number 9");
					System.out.print("Enter the option number: ");
					int opt = 0;
					String val = scan.nextLine();
					System.out.println();
					if(val.equals("1") || val.equals("2") || val.equals("3") || val.equals("4") || val.equals("9")) {
						opt = Integer.parseInt(val);	
					}
					
					switch(opt) {
					
					case 1:
						
						//Withdraw money just available with active accounts
						boolean comp = true;
						System.out.print("Enter the amount for the transaction: ");
						double aWithdraw = scan.nextDouble();
						scan.nextLine();
						System.out.print("Enter the account number from which you will withdraw the money: ");
						String accNumber = scan.nextLine();
						System.out.println("Processing your request.");
						System.out.println();
						
						List<AccountsDisplay> acDisplay = accServ.getSpecificAccount(uDriver.getUserUserName(), accNumber);
						for(AccountsDisplay accounts: acDisplay) {
							comp = aWithdraw < accounts.getAccountBalance();
						}
							if(comp != true) {
								System.out.println("Transaction canceled, insufficient funds");
							}else {
								try {
									int rowsAffected = accServ.withdrawFromAccount(aWithdraw, uDriver.getUserUserName(), accNumber);
									if(rowsAffected != 0) {
										acDisplay = accServ.getSpecificAccount(uDriver.getUserUserName(), accNumber);
										for(AccountsDisplay accounts2: acDisplay) {
											System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
											System.out.println();
										}
									}else {
										System.out.println("Sorry we could not process your request");
										System.out.println("Please try again later...");
									}
								}catch(Exception e) {
								System.out.println();
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
								}
							}
						break;
						
					case 2:
						
						//Deposit transaction
						System.out.print("Enter the amount for the transaction: ");
						double aDeposit = scan.nextDouble();
						scan.nextLine();
						System.out.print("Enter the account number where the money will be deposit: ");
						String accDNumber = scan.nextLine();
						System.out.println("Processing your request.");
						System.out.println();
						
							try {
								int rowsAffected = accServ.depositIntoAccount(aDeposit, uDriver.getUserUserName(), accDNumber);
								if(rowsAffected != 0) {
									acDisplay = accServ.getSpecificAccount(uDriver.getUserUserName(), accDNumber);
									for(AccountsDisplay accounts2: acDisplay) {
										System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
										System.out.println();
									}
								}else {
									System.out.println("Sorry we could not process your request");
									System.out.println("Please try again later...");
								}
							}catch(Exception e) {
								System.out.println();
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
							}
						
						break;
						
					case 3:
						
							//Transfer between account
							System.out.print("Enter the amount for the transaction: ");
							double aTransfer = scan.nextDouble();
							scan.nextLine();
							System.out.print("Enter the account number from which you will transfer the money: ");
							String accTransferOrigin = scan.nextLine();
							System.out.print("Enter the account number where the money will be transfered: ");
							String accTransferDest = scan.nextLine();
							System.out.print("Enter the first name of the account owner: ");
							String accTransferOwnerFirstName = scan.nextLine();
							System.out.print("Enter the Last name of the account owner: ");
							String accTransferOwnerLastName = scan.nextLine();
							System.out.println("Processing your request.");
							System.out.println();
							
							try {
								int numRows = accServ.transferBetweenAccounts(aTransfer, uDriver.getUserUserName(), accTransferOrigin, accTransferDest, accTransferOwnerFirstName, accTransferOwnerLastName);
								if(numRows != 0) {
									acDisplay = accServ.getSpecificAccount(uDriver.getUserUserName(), accTransferOrigin);
									for(AccountsDisplay accounts2: acDisplay) {
										System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
										System.out.println();
									}
								}
							}catch(Exception e) {
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
							}
							
							
						break;
						
					case 4:
							//Applying for a new account
							int newAccountStatus = 0; 
							System.out.println("Select the type of account you want to apply");
							System.out.print("press 1 for Checking account, press 2 for Joint account: ");
							int typeAccount = scan.nextInt();
							System.out.print("Enter the account opening balance: ");
							double startBalance = scan.nextDouble();
							scan.nextLine();
							System.out.println();
							
							try {
								String newAccountNumber = accServ.createNewAccount(startBalance, newAccountStatus, typeAccount, uDriver.getUserId());
								System.out.println("Processing your request, your new bank account is: "+ newAccountNumber);
							} catch (Exception e) {
								//e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
							}
						break;
						
					case 5:
						break;
						
					case 9:
						uDriver = null;
						accDriver = null;
						System.out.println();
						break;
						
					default:
						System.out.println("Please enter a valid number option!");
						break;
					}
					
					
				}else if (uDriver.getUserRole() == 1) {
					
					//Role 1 means the user is an employee
					System.out.println();
					System.out.println("What would you like to do?");
					System.out.println("1) Get users information");
					System.out.println("2) Get accounts information");
					System.out.println("3) Approve/Deny pending application account");
					System.out.println("To exit press number 9");
					System.out.print("Enter the option number: ");
					int opt = scan.nextInt();
					scan.nextLine();
					
					switch(opt) {
					
					case 1:
							//Listing all users info
							List<User> uInfo = uServ.getAllUsers();
							System.out.println();
							System.out.println("Users information List");
							System.out.println();
							for(User u: uInfo) {
								System.out.println("User Id: " + u.getUserId());
								System.out.println("First Name: " + u.getUserFirtsName());
								System.out.println("Last Name: " + u.getUserLastName());
								System.out.println("Email: " + u.getUserEmail());
								System.out.println("Username: " + u.getUserUserName());
								System.out.println();
							}
						break;
						
					case 2:
							//Retrieve Active accounts information and balances
							List<AccountsDisplay> acDisplay = accServ.getAllAccountsInfo(1);
							System.out.println();
							System.out.println("Accounts information List");
							System.out.println();
							for(AccountsDisplay accounts: acDisplay) {
								System.out.println("User: " + accounts.getUsername());
								System.out.println("Account type: " + accounts.getAccountType());
								System.out.println("Account Number: " + accounts.getAccountNumber());
								System.out.println("Account Balance: " + accounts.getAccountBalance());
								System.out.println();
							}
						break;
						
					case 3:
							//Approving or denying new accounts requests, 0 means pending, 1 means approved (it's available for transactions), 2 means canceled
							//Listing all the pending approval accounts
							List<AccountsDisplay> acDisplay2 = accServ.getAllAccountsInfo(0);
							System.out.println();
							System.out.println("List of accounts pending for approval");
							System.out.println();
							for(AccountsDisplay accounts: acDisplay2) {
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
							System.out.println();
							System.out.println("Are you sure you want to perform this action?");
							System.out.print("Press 'Y' to continue, press 'N' to cancel: ");
							String confirm = (scan.nextLine()).toLowerCase();
							if(confirm.equals("y")) {
								try {
									int rowsAffected = accServ.updateAccountStatus(accNumber, accUsername, newStatus);
									if(rowsAffected != 0) {
										System.out.println();
										System.out.println("Operation completed.");
										System.out.println();
									}
								}catch(Exception e) {
									System.out.println();
									e.printStackTrace();
									System.out.println("Sorry we could not process your request");
									System.out.println("Please try again later...");
								}
							}else {
								accNumber = null;
								accUsername = null;
								newStatus = 0;
								confirm = null;
								System.out.println("Operation canceled.");
								System.out.println();
							}
						break;
						
					case 9:
						uDriver = null;
						accDriver = null;
						System.out.println();
						break;
						
					default:
						System.out.println("Please enter a valid number option!");
						break;
					}
					
				}else if(uDriver.getUserRole() == 2) {
					
					//Role 2 means the user is a full power Admin.
					System.out.println();
					System.out.println("What would you like to do?");
					System.out.println("1) Withdraw money");
					System.out.println("2) Deposit money");
					System.out.println("3) Transfer money");
					System.out.println("4) Get users information");
					System.out.println("5) Get accounts information");
					System.out.println("6) Approve/Deny pending application account");
				    System.out.println("7) Cancel/Edit an account");
					System.out.println("To exit press number 9");
					System.out.print("Enter the option number: ");
					int opt = 0;
					String val = scan.nextLine();
					System.out.println();
					if(val.equals("1") || val.equals("2") || val.equals("3") || val.equals("4") || val.equals("5") || val.equals("6") || val.equals("7") ||  val.equals("9")) {
						opt = Integer.parseInt(val);	
					}
					
					switch(opt) {
					
					case 1:
						
						//Withdraw money just available with active accounts
						boolean comp = true;
						System.out.print("Enter the account number from which you will withdraw the money: ");
						String accNumber = scan.nextLine();
						System.out.print("Enter the account owner username: ");
						String accUsername = scan.nextLine();
						System.out.print("Enter the amount for the transaction: ");
						double aWithdraw = 0;
						String val1 = scan.nextLine();
						System.out.println();
						try { aWithdraw = Integer.parseInt(val1);}catch(NumberFormatException e) {aWithdraw = 0;System.out.print("Invalid input.");}
						System.out.println();
						
						List<AccountsDisplay> acDisplay = accServ.getSpecificAccount(accUsername, accNumber);
						for(AccountsDisplay accounts: acDisplay) {
							comp = aWithdraw < accounts.getAccountBalance();
						}
							if(comp != true) {
								System.out.println("Transaction canceled, insufficient funds");
							}else if(aWithdraw <= 0) {System.out.print("Enter a valid amount.");}
								else if(aWithdraw > 0){
								try {
									int rowsAffected = accServ.withdrawFromAccount(aWithdraw, accUsername, accNumber);
									if(rowsAffected != 0) {
										acDisplay = accServ.getSpecificAccount(accUsername, accNumber);
										for(AccountsDisplay accounts2: acDisplay) {
											
											System.out.println("Transaction completed.");
											System.out.println();
											System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
											System.out.println();
										}
									}else {
										System.out.println("Sorry we could not process your request");
										System.out.println("Please try again later...");
									}
								}catch(Exception e) {
								System.out.println();
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
								}
							}
						break;
						
					case 2:
						
						//Deposit transaction
						System.out.print("Enter the account number where the money will be deposit: ");
						String accDNumber = scan.nextLine();
						System.out.print("Enter the account owner username: ");
						String accDUsername = scan.nextLine();
						System.out.print("Enter the amount for the transaction: ");
						double aDeposit = 0;
						String val2 = scan.nextLine();
						System.out.println();
						try { aDeposit = Integer.parseInt(val2);}catch(NumberFormatException e) {System.out.print("Invalid input.");}
						System.out.println();
						
						if(aDeposit > 0) {
						
							try {
								int rowsAffected = accServ.depositIntoAccount(aDeposit, accDUsername, accDNumber);
								if(rowsAffected != 0) {
									acDisplay = accServ.getSpecificAccount(accDUsername, accDNumber);
									for(AccountsDisplay accounts2: acDisplay) {
										System.out.println("Transaction completed.");
										System.out.println();
										System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
										System.out.println();
									}
								}else {
									System.out.println("Sorry we could not process your request");
									System.out.println("Please try again later...");
								}
							}catch(Exception e) {
								System.out.println();
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
							}
						}else {
							System.out.println("Enter a valid amount.");
							}
						
						break;
						
					case 3:
						
							//Transfer between account
							System.out.print("Enter the account number from which you will transfer the money: ");
							String accTransferOrigin = scan.nextLine();
							System.out.print("Enter the account owner username: ");
							String accUsername1 = scan.nextLine();
							System.out.print("Enter the account number where the money will be transfered: ");
							String accTransferDest = scan.nextLine();
							System.out.print("Enter the account owner username: ");
							String accUsername2 = scan.nextLine();
							System.out.print("Enter the amount for the transaction: ");
							double aTransfer = 0;
							String val3 = scan.nextLine();
							System.out.println();
							try { aTransfer = Integer.parseInt(val3);}catch(NumberFormatException e) {System.out.print("Invalid input.");}
							System.out.println();
							
							if(aTransfer > 0) {
								try {
									int numRows = accServ.transferBetweenAccounts(aTransfer, accUsername1, accUsername2, accTransferOrigin, accTransferDest);
									if(numRows != 0) {
										acDisplay = accServ.getSpecificAccount(accUsername1, accTransferOrigin);
										for(AccountsDisplay accounts2: acDisplay) {
											System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
											System.out.println();
										}
										acDisplay = accServ.getSpecificAccount(accUsername2, accTransferDest);
										for(AccountsDisplay accounts2: acDisplay) {
											System.out.println("The account " + accounts2.getAccountNumber() + " current balance is: "+accounts2.getAccountBalance());
											System.out.println();
										}
									}
								}catch(Exception e) {
									e.printStackTrace();
									System.out.println("Sorry we could not process your request");
									System.out.println("Please try again later...");
								}
							}else {
								System.out.println("Enter a valid amount.");
							}
						break;
						
					case 4:
						//Listing all users info
						List<User> uInfo = uServ.getAllUsers(0);
						System.out.println();
						System.out.println("Bank users information List");
						System.out.println();
						for(User u: uInfo) {
							System.out.println("User Id: " + u.getUserId());
							System.out.println("First Name: " + u.getUserFirtsName());
							System.out.println("Last Name: " + u.getUserLastName());
							System.out.println("Email: " + u.getUserEmail());
							System.out.println("Username: " + u.getUserUserName());
							System.out.println();
						}
						List<User> uInfo1 = uServ.getAllUsers(1);
						System.out.println();
						System.out.println("Bank employees information List");
						System.out.println();
						for(User u: uInfo1) {
							System.out.println("User Id: " + u.getUserId());
							System.out.println("First Name: " + u.getUserFirtsName());
							System.out.println("Last Name: " + u.getUserLastName());
							System.out.println("Email: " + u.getUserEmail());
							System.out.println("Username: " + u.getUserUserName());
							System.out.println();
						}
						List<User> uInfo2 = uServ.getAllUsers(2);
						System.out.println();
						System.out.println("Bank admins information List");
						System.out.println();
						for(User u: uInfo2) {
							System.out.println("User Id: " + u.getUserId());
							System.out.println("First Name: " + u.getUserFirtsName());
							System.out.println("Last Name: " + u.getUserLastName());
							System.out.println("Email: " + u.getUserEmail());
							System.out.println("Username: " + u.getUserUserName());
							System.out.println();
						}
						break;
					
					case 5:
						//Retrieve Active accounts information and balances
						List<AccountsDisplay> acDisplay5 = accServ.getAllAccountsInfo(1);
						System.out.println();
						System.out.println("Active accounts information List");
						System.out.println();
						for(AccountsDisplay accounts: acDisplay5) {
							System.out.println("User: " + accounts.getUsername());
							System.out.println("Account type: " + accounts.getAccountType());
							System.out.println("Account Number: " + accounts.getAccountNumber());
							System.out.println("Account Balance: " + accounts.getAccountBalance());
							System.out.println();
						}
						//Retrieve pending accounts information and balances
						List<AccountsDisplay> acDisplay50 = accServ.getAllAccountsInfo(0);
						System.out.println();
						System.out.println("Pending accounts information List");
						System.out.println();
						for(AccountsDisplay accounts: acDisplay50) {
							System.out.println("User: " + accounts.getUsername());
							System.out.println("Account type: " + accounts.getAccountType());
							System.out.println("Account Number: " + accounts.getAccountNumber());
							System.out.println("Account Balance: " + accounts.getAccountBalance());
							System.out.println();
						}
						//Retrieve denied accounts information and balances
						List<AccountsDisplay> acDisplay52 = accServ.getAllAccountsInfo(2);
						System.out.println();
						System.out.println("Denied accounts information List");
						System.out.println();
						for(AccountsDisplay accounts: acDisplay52) {
							System.out.println("User: " + accounts.getUsername());
							System.out.println("Account type: " + accounts.getAccountType());
							System.out.println("Account Number: " + accounts.getAccountNumber());
							System.out.println("Account Balance: " + accounts.getAccountBalance());
							System.out.println();
						}
						//Retrieve canceled accounts information and balances
						List<AccountsDisplay> acDisplay53 = accServ.getAllAccountsInfo(3);
						System.out.println();
						System.out.println("Canceled accounts information List");
						System.out.println();
						for(AccountsDisplay accounts: acDisplay53) {
							System.out.println("User: " + accounts.getUsername());
							System.out.println("Account type: " + accounts.getAccountType());
							System.out.println("Account Number: " + accounts.getAccountNumber());
							System.out.println("Account Balance: " + accounts.getAccountBalance());
							System.out.println();
						}
						break;
						
					case 6:
						//Approving or denying new accounts requests, 0 means pending, 1 means approved (it's available for transactions), 2 means denied, 3 means canceled
						//Listing all the pending approval accounts
						List<AccountsDisplay> acDisplay2 = accServ.getAllAccountsInfo(0);
						System.out.println();
						System.out.println("List of accounts pending for approval");
						System.out.println();
						for(AccountsDisplay accounts: acDisplay2) {
							System.out.println("User: " + accounts.getUsername());
							System.out.println("Account type: " + accounts.getAccountType());
							System.out.println("Account Number: " + accounts.getAccountNumber());
							System.out.println("Account Balance: " + accounts.getAccountBalance());
							System.out.println();
						}
						
						System.out.print("Enter the account number: ");
						String accNumberAD = scan.nextLine();
						System.out.print("Enter the account owner username: ");
						String accUsernameAD = scan.nextLine();
						System.out.print("Press 1 to Approve account, press 2 to deny account: ");
						int newStatus = 0;
						String val4 = scan.nextLine();
						System.out.println();
						try { newStatus = Integer.parseInt(val4);}catch(NumberFormatException e) {System.out.print("Invalid input.");}
						System.out.println();
						System.out.println("Are you sure you want to perform this action?");
						System.out.print("Press 'Y' to continue, press 'N' to cancel: ");
						String confirm = (scan.nextLine()).toLowerCase();
						if(confirm.equals("y")) {
							try {
								int rowsAffected = accServ.updateAccountStatus(accNumberAD, accUsernameAD, newStatus);
								if(rowsAffected != 0) {
									System.out.println();
									System.out.println("Operation completed.");
									System.out.println();
								}
							}catch(Exception e) {
								System.out.println();
								e.printStackTrace();
								System.out.println("Sorry we could not process your request");
								System.out.println("Please try again later...");
							}
						}else {
							accNumberAD = null;
							accUsernameAD = null;
							newStatus = 0;
							confirm = null;
							System.out.println("Operation canceled.");
							System.out.println();
						}
						break;
						
					case 7:
						break;
						
					case 9:
						uDriver = null;
						accDriver = null;
						System.out.println();
						break;
						
					default:
						System.out.println("Please enter a valid number option!");
						break;
					}
					
				}

		  } 
		}
		
		System.out.println("Goodbye!");
		scan.close();
	}
}
