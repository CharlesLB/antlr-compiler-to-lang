/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa um literal float.
 *
 * @Expr float
 *
 * @Example 1.0
 * @Example 3.14
 */
public class FloatLiteral extends Expr {
	private float value;

	public FloatLiteral(float value) {
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
