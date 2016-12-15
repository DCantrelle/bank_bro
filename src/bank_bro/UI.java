package bank_bro;

import java.util.Scanner;

public class UI {
	
	private Scanner in;
	private String regPrompt;
	private String moneyPrompt;
	
	public UI() {
		this.in = new Scanner(System.in);
		this.regPrompt = ">";
		this.moneyPrompt = "$";
	}
	
	public void promptDisplay(String prompt) {
		System.out.println(prompt);
	}
	
	public String promptQuestion(String question) {
		String response;
		System.out.println(question);
		System.out.print(this.regPrompt);
		response = in.nextLine();
		response.trim();
		return response;
	}
	
	public boolean is2deciaml(String number) {
		int decimalPointLoc = number.indexOf('.');
		int length = number.length() - 1;
		if(length - decimalPointLoc <= 2)
			return true;
		else 
			return false;
	}
	
	public double moneyQuestion(String question) {
		String response;
		Double value, intValue;
		System.out.println(question);
		while(true) {
			System.out.print(this.moneyPrompt);
			response = in.nextLine();
			response.trim();
			try {
				value = Double.parseDouble(response);
				intValue = (double) value.intValue();
				if(value - intValue == 0.0 || is2deciaml(value.toString()))
					return value;
				else {
					System.out.println("Invalid value.");
				}
				
			} catch(NumberFormatException E) {
						System.out.println("Invalid value.");
					}
		}
	}
	
	private void printOptions(String[] options) {
		int length = options.length;
		for(int i = 0; i < length; i++) {
			if(length - i >= 3) {
				System.out.printf("%d. %-30s %d. %-30s %d. %-30s %n", i, options[i], i+1, options[i+1], i+2, options[i+2]);
				i += 2;
			}
			else if(length - i == 2) {
				System.out.printf("%d. %-30s %d. %-30s %n", i, options[i], i+1, options[i+1]);
				i += 1;
			}
			else if(length - i == 1) {
				System.out.printf("%d. %-30s %n", i, options[i]);
			}
		}
	}
	
	private int response(int top, int bottom) {
		String input;
		int response;
		System.out.println("Please enter a integer between " + bottom + " and " + top + ".");
		System.out.print(this.regPrompt);
		input = in.nextLine(); //Recieve line of input
		input = input.trim();
		try{
			response = Integer.parseInt(input);
			if(response < bottom || response > top) {
				System.out.println("Invalid input please try again.");
				return -1;
			}
			else {
				return response;
			}
		} catch(NumberFormatException e) {
			System.out.println("Invalid input please try again.");
			return -1;
		}
	}

	public int promptMenu(String question, String[] options) {
		int response;
		System.out.println(question);
		printOptions(options);
		System.out.println();
		do {
			response = this.response(options.length - 1, 0);
			} while(response == -1);
		return response;
	}
	
	
	
	
	
	//testing functions
	public String TESTpromptQuestion(String question, String userInput) {
		Scanner test = new Scanner(userInput);
		String response;
		//System.out.println(question);
		//System.out.print(this.regPrompt);
		response = test.nextLine();
		response.trim();
		test.close();
		return response;
		
	}
	
	public int TESTresponse(int top, int bottom, String userInput) {
		Scanner test = new Scanner(userInput);
		
		String input;
		int response;
		System.out.println("Please enter a integer between " + bottom + " and " + top + ".");
		System.out.print(this.regPrompt);
		input = test.nextLine(); //Recieve line of input
		input = input.trim();
		System.out.println(input);
		test.close();
		try{
			response = Integer.parseInt(input);
			if(response < bottom || response > top) {
				System.out.println("Invalid input please try again.");
				return -1;
			}
			else {
				return response;
			}
		} catch(NumberFormatException e) {
			System.out.println("Invalid input please try again.");
			return -1;
		}
		
	}

	public double TESTmoneyQuestion(String question, String userInput) {
		Scanner test = new Scanner(userInput);
		
		String response;
		Double value, intValue;
		System.out.println(question);
		while(true) {
			System.out.print(this.moneyPrompt);
			response = test.nextLine();
			response.trim();
			System.out.println(response);
			try {
				value = Double.parseDouble(response);
				intValue = (double) value.intValue();
				if(value - intValue == 0.0 || is2deciaml(value.toString())) {
					test.close();
					return value;
				}
				else {
					System.out.println("Invalid value.");
				}
				
			} catch(NumberFormatException E) {
						System.out.println("Invalid value.");
					}
		}
	}

	public int TESTpromptMenu(String question, String[] options, String userInput) {
		Scanner test = new Scanner(userInput);
		String line;
		int response;
		System.out.println(question);
		printOptions(options);
		System.out.println();
		do {
			line = test.nextLine(); // for testing purposes 
			response = this.TESTresponse(options.length - 1, 0, line);
			} while(response == -1);
		test.close();
		return response;
	}
}
