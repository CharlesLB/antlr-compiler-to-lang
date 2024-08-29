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

		float left = convertToFloat(leftValue);
		float right = convertToFloat(rightValue);

		return left % right;
	}

	private float convertToFloat(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).floatValue();
		} else if (value instanceof Float) {
			return (Float) value;
		} else {
			throw new RuntimeException("Unsupported type for subtraction: " + value.getClass().getName());
		}
	}
}
