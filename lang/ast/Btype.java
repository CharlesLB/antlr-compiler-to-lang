package lang.ast;

import java.util.HashMap;

public class Btype extends Type {

	private String type;

	public Btype(int l, int c, String type) {
		super(l, c);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// @Override
	public String toString() {
		return type;
	}

	public int interpret(HashMap<String, Integer> m) {
		return m.get(type);
	}
}