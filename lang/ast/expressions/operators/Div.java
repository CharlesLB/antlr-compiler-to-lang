/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
import lang.ast.definitions.Expr;

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
	public Div(Expr l, Expr r) {
		super(l, r);
	}

	@Override
	public String toString() {
		return getLeft().toString() + " / " + getRight().toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
