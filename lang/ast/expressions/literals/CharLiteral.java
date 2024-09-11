/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Essa classe representa um literal caractere.
 *
 * @Expr 'char'
 *
 * @Example 'a'
 * @Example '\n'
 */
public class CharLiteral extends Expr {
	private String value;

	public CharLiteral(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
