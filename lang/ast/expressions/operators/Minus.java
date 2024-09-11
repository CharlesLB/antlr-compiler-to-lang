/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
import lang.ast.definitions.Expr;

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

	public Minus(Expr l, Expr r) {
		super(l, r);
	}

	public String toString() {
		String s = getLeft().toString();
		if (getLeft() instanceof Plus) {
			s = "(" + s + ")";
		}
		return s + " - " + getRight().toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

}
