package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

/**
 * Essa classe representa a comparação de menor que entre duas expressões.
 * 
 * @Expr Expr < Expr
 * 
 * @Example 2 < 1
 * @Example 1.0 < 2.0
 * @Example 'a' < 'b'
 * @Example false < true
 * 
 * @Error Null < 1 -> Null values cannot be compared
 * @Error 1 < null -> Null values cannot be compared
 * @Error 1 < 2.0 -> Unsupported type for comparison: java.lang.Integer
 */
public class LessThan extends BinOP {
	public LessThan(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		// Garante que os valores de left e right não são nulos
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " < " + rightStr + ")";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be compared");
		}

		double left = convertToFloat(leftValue);
		double right = convertToFloat(rightValue);

		Object aux = left < right ? true : false;
		System.out.println("-- LessThan: " + left + " < " + right + " =" + aux);

		return left < right ? true : false;
	}

	private double convertToFloat(Object value) {
		if (value instanceof Integer) {
			return ((Integer) value).doubleValue();
		} else if (value instanceof Float) {
			return (Float) value;
		} else if (value instanceof Character) {
			return (double) ((Character) value).charValue();
		} else if (value instanceof Boolean) {
			return (Boolean) value ? 1.0 : 0.0;
		} else {
			throw new RuntimeException("Unsupported type for comparison: " + value.getClass().getName());
		}
	}
}
