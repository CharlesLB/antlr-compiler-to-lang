package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.expressions.Expr;
import lang.ast.expressions.ID;

public class Assign extends Cmd {

	private ID id;
	private Expr e;

	public Assign(int l, int c, ID id, Expr e) {
		super(l, c);
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

	public int interpret(HashMap<String, Integer> m) {
		int x = e.interpret(m);
		m.put(id.getName(), x);
		return x;
	}
}