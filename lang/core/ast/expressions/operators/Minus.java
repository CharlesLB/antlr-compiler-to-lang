/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.operators;

import java.util.HashMap;

import lang.core.ast.expressions.BinOP;
import lang.test.visitor.Visitor;
import lang.core.ast.definitions.Expr;

/**
 * Essa classe representa a subtração de duas expressões.
 * 
 * @Expr Expr - Expr
 * 
 * @Example 2 - 1
 * @Example 1.0 - 2.0
 * @Error Int + Float -> Unsupported types for subtraction
 * @Error Null - 1 -> Null values cannot be used in subtraction
 */
public class Minus extends BinOP {

	public Minus(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	public String toString() {
		String s = getLeft().toString();
		if (getLeft() instanceof Plus) {
			s = "(" + s + ")";
		}
		return s + " - " + getRight().toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be used in subtraction");
		}

		if (leftValue instanceof Float || rightValue instanceof Float) {
			return ((Number) leftValue).floatValue() - ((Number) rightValue).floatValue();
		}

		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			return (Integer) leftValue - (Integer) rightValue;
		}

		throw new RuntimeException("Unsupported types for subtraction: " + leftValue.getClass().getName() + " and "
				+ rightValue.getClass().getName());

	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
