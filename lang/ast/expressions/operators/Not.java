/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa a operação de negação lógica de uma expressão.
 *
 * @Expr !Expr
 *
 * @Example !true
 * @Error !1 -> Unsupported type for logical negation: java.lang.Integer
 * @Error !null -> Null value cannot be negated
 */
public class Not extends Expr {

	private Expr expr;

	public Not(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "!" + expr.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
