/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um acesso a um array.
 *
 * @Parser exp '[' exp ']'
 *
 * @Example array[0]
 */
public class ArrayAccess extends Expr {
	private Expr arrayExpr;
	private Expr indexExpr;

	public ArrayAccess(Expr arrayExpr, Expr indexExpr) {
		this.arrayExpr = arrayExpr;
		this.indexExpr = indexExpr;
	}

	public Expr getArray() {
		return arrayExpr;
	}

	public Expr getIndex() {
		return indexExpr;
	}

	@Override
	public String toString() {
		return arrayExpr.toString() + "[" + indexExpr.toString() + "]";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
