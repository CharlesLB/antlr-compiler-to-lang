package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Essa classe representa um literal inteiro.
 * 
 * @Expr int
 * 
 * @Example 1
 * @Example 42
 * @Example -1
 */
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