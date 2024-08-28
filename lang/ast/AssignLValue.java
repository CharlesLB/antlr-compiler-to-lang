package lang.ast;

/*
 * Esta classe representa um comando de atribuição.
 * LValue = Expr
 */

import java.util.HashMap;

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

	public int interpret(HashMap<String, Integer> m) {
		int x = e.interpret(m);
		return x;
	}
}
