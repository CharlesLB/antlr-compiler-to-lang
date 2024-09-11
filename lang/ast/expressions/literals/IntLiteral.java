/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa um literal inteiro.
 *
 * @Expr int
 *
 * @Example 1
 * @Example 42
 * @Example -1
 */
public class IntLiteral extends Expr {
	private int value;

	public IntLiteral(int v) {
		this.value = v;
	}

	public int getValue() {
		return value;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}