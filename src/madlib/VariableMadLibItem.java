package madlib;

import java.util.Scanner;

/**
 * A MadLibItem that waits for input from a user based on a prompt
 */
public class VariableMadLibItem extends MadLibItem {
	/**
	 * The type of word this MadLibItem prompts for (eg 'adjective' or 'plural noun')
	 */
	String type;
	/**
	 * The scanner this MadLibItem should use when prompting for input
	 */
	Scanner scan;
	
	/**
	 * @param type The type of word this MadLibItem should prompt for (eg 'adjective or plural noun')
	 * @param scan The scanner this MadLibItem should use when prompting for input
	 */
	public VariableMadLibItem(String type, Scanner scan) {
		this.type = type;
		this.scan = scan;
	}
	
	/**
	 * Prints out a prompt and returns the user's response
	 * @return The user's input
	 */
	@Override
	public String makeString() {
		System.out.print("Enter a " + this.type + ": ");
		return this.scan.nextLine();
	}
	
	@Override
	public String toString() {
		return "Variable: '" + this.type + "'";
	}
}
