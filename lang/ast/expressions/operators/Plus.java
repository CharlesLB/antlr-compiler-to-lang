/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.Expr;
import lang.ast.expressions.BinOP;

/**
 * Essa classe representa a operação de soma de duas expressões.
 * 
 * @Expr Expr + Expr
 * 
 * @Example 2 + 1
 * @Example 1.0 + 2.0
 * @Error Int + Float -> Unsupported types for plus
 * @Error Null + 1 -> Null values cannot be used in plus
 */
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
