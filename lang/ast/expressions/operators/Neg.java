package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class Neg extends Expr {

	private Expr expr;

	public Neg(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "-" + expr.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object value = expr.interpret(m);

		if (value == null) {
			throw new RuntimeException("Null value cannot be negated");
		}

		float negatedValue = convertToFloat(value);

		return -negatedValue;
	}

	private float convertToFloat(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).floatValue();
		} else if (value instanceof Float) {
			return (Float) value;
		} else {
			throw new RuntimeException("Unsupported type for negation: " + value.getClass().getName());
		}
	}
}
