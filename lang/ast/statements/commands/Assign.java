/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import lang.ast.expressions.ID;
import visitors.Visitor;

/**
 * Representa um comando de atribuição.
 *
 * @Parser id ‘=’ exp ‘;’
 *
 * @Example x = 1;
 *
 * @Info O tipo de X NÃO é inferido.
 *
 */
public class Assign extends Cmd {
	private ID id;
	private Expr e;

	public Assign(ID id, Expr e) {
		this.id = id;
		this.e = e;
	}

	public ID getID() {
		return id;
	}

	public Expr getExp() {
		return e;
	}

	public String toString() {
		return id.toString() + " = " + e.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}