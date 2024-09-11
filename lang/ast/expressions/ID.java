/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import visitors.Visitor;

import lang.ast.definitions.Expr;

/**
 * Representa um identificador.
 *
 * @Parser ID
 *
 * @Example x
 */
public class ID extends Expr {

	private String l;

	public ID(String name) {
		this.l = name;
	}

	public String getName() {
		return l;
	}

	// @Override
	public String toString() {
		return l;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}