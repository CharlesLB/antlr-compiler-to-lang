package lang.ast.expressions.operators;

/*
 * Esta classe representa uma expressão de soma.
 * Expr + Expr
 */

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

public class Plus extends BinOP {

	public Plus(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	public String toString() {
		String s = getLeft().toString();
		if (getLeft() instanceof Plus) {
			s = "(" + s + ")";
		}
		return s + " + " + getRight().toString();
	}

	// @Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be used in modulo operation");
		}

		if (leftValue instanceof Float || rightValue instanceof Float) {
			return ((Number) leftValue).floatValue() + ((Number) rightValue).floatValue();
		}

		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			return (Integer) leftValue + (Integer) rightValue;
		}

		throw new RuntimeException("Unsupported types for plus: " + leftValue.getClass().getName() + " and "
				+ rightValue.getClass().getName());

	}

}
