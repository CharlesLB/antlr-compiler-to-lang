/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
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
	public LessThan(Expr l, Expr r) {
		super(l, r);
	}

	@Override
	public String toString() {
		/* Garante que os valores de left e right não são nulos */
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " < " + rightStr + ")";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
