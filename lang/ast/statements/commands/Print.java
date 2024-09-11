/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um comando de impressão.
 *
 * @Parser print exp ‘;’
 *
 * @Example print 1;
 */
public class Print extends Cmd {
	private Expr expr;

	public Print(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "print " + expr.toString() + ";";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
