package lang.ast.lvalue;

/*
 * Esta classe representa um comando de atribuição.
 * LValue = Expr
 */

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

public class AssignLValue extends Cmd {

	private LValue id;
	private Expr e;

	public AssignLValue(int l, int c, LValue id, Expr e) {
		super(l, c);
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

	public Object interpret(HashMap<String, Object> m) {
		Object x = e.interpret(m);
		return x;
	}
}
