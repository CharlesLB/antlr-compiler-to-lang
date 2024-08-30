package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

public class Mod extends BinOP {

	public Mod(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		return getLeft().toString() + " % " + getRight().toString();
	}

	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be used in modulo operation");
		}

		if (leftValue instanceof Float || rightValue instanceof Float) {
			return ((Number) leftValue).floatValue() % ((Number) rightValue).floatValue();
		}

		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			return (Integer) leftValue % (Integer) rightValue;
		}

		throw new RuntimeException("Unsupported types for mod: " + leftValue.getClass().getName() + " and "
				+ rightValue.getClass().getName());

	}
}
