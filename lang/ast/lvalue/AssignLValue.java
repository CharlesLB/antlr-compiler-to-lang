/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um comando de atribuição.
 * 
 * @Parser LValue = Expr
 * 
 * @Example x = 10
 */
public class AssignLValue extends Cmd {
	private LValue id;
	private Expr e;

	public AssignLValue(LValue id, Expr e) {
		this.id = id;
		this.e = e;
	}

	public LValue getID() {
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
