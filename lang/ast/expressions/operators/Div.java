package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

public class Div extends BinOP {

	public Div(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		return getLeft().toString() + " / " + getRight().toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be used in division");
		}

		// Verifica divisão por zero

		System.out.println("Node Div: ");

		if (leftValue instanceof Float || rightValue instanceof Float) {
			if ((Float) rightValue == 0.0) {
				throw new RuntimeException("Division by zero");
			}

			return ((Number) leftValue).floatValue() / ((Number) rightValue).floatValue();
		}
		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			if ((Integer) rightValue == 0.0) {
				throw new RuntimeException("Division by zero");
			}

			return (Integer) leftValue / (Integer) rightValue;
		}

		throw new RuntimeException("Unsupported types for division: " + leftValue.getClass().getName() + " and "
				+ rightValue.getClass().getName());

	}
}
