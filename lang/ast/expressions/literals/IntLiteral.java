package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

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