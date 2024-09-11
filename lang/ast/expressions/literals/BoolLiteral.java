/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa um literal booleano.
 *
 * @Expr true | false
 *
 * @Example true
 * @Example false
 */
public class BoolLiteral extends Expr {
	private Boolean value;

	public BoolLiteral(Boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
