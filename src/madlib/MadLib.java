package madlib;

import java.util.Scanner;

public class MadLib {
	MadLibItem[] items;
	
	public MadLib(MadLibItem[] items) {
		this.items = items;
	}
	
	public String play() {
		StringBuilder buf = new StringBuilder();
		for (MadLibItem item : this.items) {
			buf.append(item.makeString());
		}
		return buf.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for (MadLibItem item : this.items) {
			buf.append(item);
			buf.append("\n");
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		String rawMadLib = "Hello there <name>. Welcome to my <adjective> home.";
		Scanner scan = new Scanner(System.in);
		MadLibParser p = new MadLibParser(rawMadLib, scan);
		MadLib madLib;
		try {
			madLib = p.createMadLib();
		} catch (MadLibParseException e) {
			System.out.println("Failed to parse MadLib");
			return;
		}
		System.out.println(madLib.play());
	}
}
