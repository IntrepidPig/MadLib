package madlib;

public class ConstantMadLibItem extends MadLibItem {
	String data;
	
	public ConstantMadLibItem(String data) {
		this.data = data;
	}
	
	@Override
	public String makeString() {
		return this.data;
	}
	
	@Override
	public String toString() {
		return "Constant: '" + this.data + "'";
	}
}
