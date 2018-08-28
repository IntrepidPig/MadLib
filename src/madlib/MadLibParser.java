package madlib;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses a MadLib from a String. The String should be formatted as plain text, where anything between
 * angled brackets will be converted into a prompt to the user for the item specified in the brackets.
 * For example, the string 'Hello there <name>' will generate a MadLib that will have 'Hello there ' and
 * then prompt the user for a 'name' and put the user's input in it's place.
 */
public class MadLibParser {
	/**
	 * The entire unmodified input
	 */
	String raw;
	/**
	 * The String that still needs to be parsed
	 */
	String leftovers;
	/**
	 * The Scanner to be used for new VariableMadLibItems
	 */
	Scanner scan;
	
	/**
	 * @param input The String the parser will parse
	 * @param scan The Scanner to be used for new VariableMadLibItems
	 */
	public MadLibParser(String input, Scanner scan) {
		this.raw = input;
		this.leftovers = input;
		this.scan = scan;
	}
	
	/**
	 * Creates a MadLib from the input given
	 * @return A MadLib based on the String input
	 * @throws MadLibParseException If there is an unclosed angle bracket
	 */
	MadLib createMadLib()
		throws MadLibParseException {
		ArrayList<MadLibItem> items = new ArrayList<>();
		
		// Try to take MadLibItems until nothing else succeeds
		MadLibItem item = this.takeMadLibItem();
		while (item != null) {
			items.add(item);
			item = this.takeMadLibItem();
		}
		
		if (!this.leftovers.isEmpty()) {
			// Should never happen
			// takeConstantMadLibItem should consume anything that's left
			throw new MadLibParseException();
		}
		
		MadLibItem[] itemsArray = new MadLibItem[items.size()];
		items.toArray(itemsArray);
		return new MadLib(itemsArray);
	}
	
	/**
	 * Tries to take any MadLibItem from the leftover String
	 * @return A MadLibItem if one is found, or null if nothing is found
	 * @throws MadLibParseException If there is an unclosed angle bracket
	 */
	MadLibItem takeMadLibItem()
		throws MadLibParseException {
		// First try to take a ConstantMadLibItem
		MadLibItem item = this.takeConstantMadLibItem();
		if (item != null) {
			return item;
		}
		// Then try to take a VariableMadLibItem
		item = this.takeVariableMadLibItem();
		return item;
	}
	
	/**
	 * Tries to take a constant element in the MadLib from the leftover string
	 * @return A MadLibItem or null if there was no ConstantMadLibItem next
	 */
	ConstantMadLibItem takeConstantMadLibItem() {
		for (int i = 0; i < leftovers.length(); i++) {
			if (this.leftovers.charAt(i) == '<') {
				String val = this.leftovers.substring(0, i);
				this.leftovers = this.leftovers.substring(i);
				if (i == 0) {
					return null;
				} else {
					return new ConstantMadLibItem(val);
				}
			}
		}
		
		String val = this.leftovers;
		this.leftovers = "";
		if (val.length() == 0) {
			return null;
		} else {
			return new ConstantMadLibItem(val);
		}
	}
	
	/**
	 * Tries to take a VariableMadLibItem (and item between angle brackets)
	 * @return A VariableMadLibItem or null if there was none next
	 * @throws MadLibParseException If there is an unclosed angle bracket
	 */
	VariableMadLibItem takeVariableMadLibItem()
		throws MadLibParseException {
		// Check if the input is not empty and starts with an opening angle bracket
		if (this.leftovers.length() > 0) {
			if (this.leftovers.charAt(0) != '<') {
				return null;
			}
		} else {
			return null;
		}
		
		// Find the closing angle bracket and return the string up to it,
		// excluding the open angle bracket
		for (int i = 1; i < this.leftovers.length(); i++) {
			if (this.leftovers.charAt(i) == '>') {
				String val = this.leftovers.substring(1, i);
				this.leftovers = this.leftovers.substring(i + 1);
				return new VariableMadLibItem(val, this.scan);
			}
		}
		
		// The closing angle bracket was not found
		throw new MadLibParseException();
	}
}
