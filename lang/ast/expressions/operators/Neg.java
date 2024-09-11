/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa a operação de negação de uma expressão.
 *
 * @Expr -Expr
 *
 * @Example -2
 * @Example -1.0
 * @Error -"string" -> Unsupported type for negation
 * @Error -null -> Null value cannot be negated
 */
public class Neg extends Expr {
	private Expr expr;

	public Neg(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "-" + expr.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
