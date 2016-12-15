/**
 * 
 */
package bank_bro;

import java.util.ArrayList;

/**
 * @author DanielsMac
 *
 */
public class Top {

	/**
	 * @param args
	 */
	static String UsernamesAndPasswordsFile = "\\\\Mac\\Home\\Desktop\\Windows\\Documents\\bankBroDatabase\\UsernamesAndPasswords.txt";
    static String TransactionsFile = "\\\\Mac\\Home\\Desktop\\Windows\\Documents\\bankBroDatabase\\Transactions.txt";
    static UI ui = new UI();
	static Database database;
	static Authenticator authenticator;
	
	public static void main(String[] args) {
		
	ArrayList<String> options = new ArrayList<String>();
	ArrayList<String> errors = new ArrayList<String>();
	String state = "welcome";
	boolean exit = false;
	int choice;
	String username, username2;
	String password, password2;
	double val;
	boolean login, logout;
	char flag;
	String simplePrompt;
		
	try {
		authenticator = new Authenticator(UsernamesAndPasswordsFile, TransactionsFile);
		database = authenticator.getDatabase();
	} catch (Exception e) {
		ui.promptDisplay("Files are incorrect \nNow exiting");
		exit = true;
	}
	
	while(!exit) {
		if(state == null) {
			exit = true;
			ui.promptDisplay("System error.");
			database.save();
		}
		
		else if(state == "exit") {
			exit = true;
			database.save();
			ui.promptDisplay("Goodbye.");
		}
		
		else if(state == "welcome") {
			ui.promptDisplay("Welcome to Bank Bro");
			options.clear();
			options.add(0, "Exit");
			options.add(1, "Login");
			options.add(2, "Create User");
			choice = ui.promptMenu("Please select an option", options.toArray(new String[options.size()]));
			if(choice == 0) {
				state = "exit";
			}
			else if(choice == 1) {
				state = "login";
			}
			else if(choice == 2) {
				state = "create user";
			}
		}
		
		else if(state == "login") {
			username = ui.promptQuestion("Username: ");
			password = ui.promptQuestion("Password: ");
			login = authenticator.loginUser(username, password);
			if(login) {
				state = "logged in";
			}
			else {
				ui.promptDisplay("Incorrect username or password.");
				ui.promptDisplay("Login failed. try again.\n");
				state = "welcome";
			}
		}
		
		else if(state == "logout") {
			logout = authenticator.logOut();
			if(logout) {
				ui.promptDisplay("Logged out. \nHave a nice day.\n");
				state = "welcome";
			}
			else { // system error
				state = null;
			}
		}
		
		else if(state == "create user") {
			username = ui.promptQuestion("Username: ");
			ui.promptDisplay("Password must be atleast 6 char. \nPassword must contain atleast 1 uppercase letter A-Z. \nPassword must contain atleast 1 lowercase letter a-z. \nPassword must contain atleast 1 simple symbol. \nPassword must contain atleast 1 number 0-9. \n");
			password = ui.promptQuestion("Password: ");
			password2 = ui.promptQuestion("ReType Password: ");
			if (password.equals(password2)) {
				errors.clear();
				authenticator.createUser(username, password, errors);
				
				if(errors.isEmpty()) {
					ui.promptDisplay("Success new user " + username + " created.\n");
					state = "welcome";
				}
				else {
					ui.promptDisplay("Fail");
					for(int i = 0; i < errors.size(); i++) {
						ui.promptDisplay(errors.get(i));
					}
					ui.promptDisplay("User " + username + " not created please try again\n");
					state = "welcome";
				}
			}
			else {
				ui.promptDisplay("Fail");
				ui.promptDisplay("Passwords did not match. \nUser " + username + " not created please try again\n");
				state = "welcome";
			}
		}
		
		else if(state == "logged in") {
			options.clear();
			options.add(0, "Logout");
			options.add(1, "Deposit");
			options.add(2, "Withdraw");
			options.add(3, "Transfer");
			options.add(4, "View history");
			options.add(5, "Get balance");
			choice = ui.promptMenu("Please select an option", options.toArray(new String[options.size()]));
			if(choice == 0) {
				state = "logout";
			}
			else if(choice == 1) {
				state = "deposit";
			}
			else if(choice == 2) {
				state = "withdraw";
			}
			else if(choice == 3) {
				state = "transfer";
			}
			else if(choice == 4) {
				state = "history";
			}
			else if(choice == 5) {
				state = "balance";
			}
		}
		
		else if(state == "deposit") {
			val = ui.moneyQuestion("Please type an amount to deposit:");
			flag = database.deposit(val);
			if(flag == 'u') {
				ui.promptDisplay("User not logged in.\n");
				state = null;
			}
			else if(flag == 'a') {
				ui.promptDisplay("Invald deposit ammout.\n");
				state = "logged in";
			}
			else if(flag == 't') {
				ui.promptDisplay("Deposit successful.\n");
				state = "balance";
			}
		}
		
		else if(state == "withdraw") {
			val = ui.moneyQuestion("Please type an amount to withdraw:");
			flag = database.withdraw(val);
			if(flag == 'u') {
				ui.promptDisplay("Error: User not logged in.\n");
				state = null;
			}
			else if(flag == 'a') {
				ui.promptDisplay("Error: Invald deposit ammout.\n");
				state = "logged in";
			}
			else if(flag == 'f') {
				ui.promptDisplay("Error: Insufficient funds.\n");
				state = "logged in";
			}
			else if(flag == 't') {
				ui.promptDisplay("withdraw successful.\n");
				state = "balance";
			}
		}
		
		else if(state == "transfer") {
			username2 = ui.promptQuestion("Please enter destination username:");
			val = ui.moneyQuestion("Please type an amount to transfer:");
			flag = database.transfer(val, username2);
			if(flag == 'u') {
				ui.promptDisplay("Error: User not logged in.\n");
				state = null;
			}
			else if(flag == 'a') {
				ui.promptDisplay("Error: Invald transfer ammout.\n");
				state = "logged in";
			}
			else if(flag == 'f') {
				ui.promptDisplay("Error: Insufficient funds.\n");
				state = "logged in";
			}
			else if(flag == 'd') {
				ui.promptDisplay("Error: Username not found.\n");
				state = "logged in";
			}
			else if(flag == 's') {
				ui.promptDisplay("Error: Can not send money to yourself.\n");
				state = "logged in";
			}
			else if(flag == 't') {
				ui.promptDisplay("transfer successful.\n");
				state = "balance";
			}
		}
		
		else if(state == "history") {
			ui.promptDisplay("Transaction History:");
			ui.promptDisplay(database.getHistory());
			state = "logged in";
		}
		
		else if(state == "balance") {
			val = database.getBalance();
			simplePrompt = "Current balance = " + val + "\n";
			ui.promptDisplay(simplePrompt);
			state = "logged in";
		}
	}
	

	}
}
