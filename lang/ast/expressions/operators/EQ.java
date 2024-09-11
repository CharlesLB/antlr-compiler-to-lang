/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
import lang.ast.definitions.Expr;

/**
 * Essa classe representa a comparação de igualdade entre duas expressões.
 *
 * @Expr Expr == Expr
 *
 * @Example 2 == 1
 * @Example 1.0 == 2.0
 * @Example true == false
 * @Example 'a' == 'b'
 * @Example 'a' == null
 */
public class EQ extends BinOP {
	public EQ(Expr l, Expr r) {
		super(l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return leftStr + " == " + rightStr;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}