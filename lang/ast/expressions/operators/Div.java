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

		// Converte os valores para float se forem inteiros ou floats
		float left = convertToFloat(leftValue);
		float right = convertToFloat(rightValue);

		// Verifica divisão por zero
		if (right == 0.0) {
			throw new RuntimeException("Division by zero");
		}

		Object aux = left / right;
		System.out.println("Node Div: " + aux);

		return left / right;
	}

	private float convertToFloat(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).floatValue();
		} else if (value instanceof Float) {
			return (Float) value;
		} else {
			throw new RuntimeException("Unsupported type for division: " + value.getClass().getName());
		}
	}
}
