/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.operators;

import java.util.HashMap;

import lang.core.ast.expressions.BinOP;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;
import lang.core.ast.definitions.Expr;

/**
 * Essa classe representa a divisão de duas expressões.
 * 
 * @Expr Expr / Expr
 * 
 * @Example 2 / 1
 * @Example 1.0 / 2.0
 * @Error Int / Float -> Unsupported types for division
 * @Error Null / 1 -> Null values cannot be used in division
 * @Error 1 / 0 -> Division by zero
 */
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

		if (leftValue instanceof Float || rightValue instanceof Float) {
			/* Verifica divisão por zero */
			if ((Float) rightValue == 0.0) {
				throw new RuntimeException("Division by zero");
			}

			return ((Number) leftValue).floatValue() / ((Number) rightValue).floatValue();
		}
		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			/* Verifica divisão por zero */
			if ((Integer) rightValue == 0.0) {
				throw new RuntimeException("Division by zero");
			}

			return (Integer) leftValue / (Integer) rightValue;
		}

		throw new RuntimeException("Unsupported types for division: " + leftValue.getClass().getName() + " and "
				+ rightValue.getClass().getName());

	}

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}
