/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa um literal nulo.
 *
 * @Expr null
 *
 * @Example null
 *
 * @Info Quando uma variável é declarada, ela é inicializada com null.
 */
public class NullLiteral extends Expr {
	public NullLiteral() {
	}

	@Override
	public String toString() {
		return "null";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
