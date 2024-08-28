package lang.ast;

import java.util.HashMap;

public class IntLiteral extends Expr {

	private int l;

	public IntLiteral(int l, int c, int v) {
		super(l, c);
		this.l = v;
	}

	public int getValue() {
		return l;
	}

	// @Override
	public String toString() {
		return "" + l;
	}

	public int interpret(HashMap<String, Integer> m) {
		return l;
	}
}