package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class IntLiteral extends Expr {

	private int value;

	public IntLiteral(int value, int c, int v) {
		super(value, c);
		this.value = v;
	}

	public int getValue() {
		return value;
	}

	// @Override
	public String toString() {
		return "" + value;
	}

	public Object interpret(HashMap<String, Object> m) {
		System.out.println("Node IntLiteral: " + value);
		return value;
	}
}