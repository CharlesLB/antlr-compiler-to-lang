/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.definitions.Expr;
import lang.ast.expressions.BinOP;
import visitors.Visitor;

/**
 * Essa classe representa a operação de comparação de desigualdade entre duas
 * expressões.
 *
 * @Expr Expr != Expr
 *
 * @Example 2 != 1
 * @Example 1.0 != 2.0
 * @Example true != false
 * @Example 'a' != 'b'
 * @Example 'a' != null -> Null values cannot be compared GABRIELLA, por que?
 */
public class NotEq extends BinOP {
	public NotEq(Expr l, Expr r) {
		super(l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return leftStr + " != " + rightStr;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}