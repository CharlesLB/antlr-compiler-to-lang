/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
import lang.ast.definitions.Expr;

/**
 * Essa classe representa a operação lógica AND entre duas expressões.
 *
 * @Expr Expr && Expr
 *
 * @Example true && false
 * @Example false && true
 * @Error Null && true -> Null values cannot be used in logical operations
 * @Error 1 && 2 -> Unsupported type for logical operation: java.lang.Integer
 */
public class And extends BinOP {
	public And(Expr l, Expr r) {
		super(l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " && " + rightStr + ")";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}