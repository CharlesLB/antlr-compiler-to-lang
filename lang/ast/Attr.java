package lang.ast;

/*
 * Esta classe representa um comando de atribuição.
 * ID = Expr
 */

import java.util.HashMap;

public class Attr extends Node {

	private ID id;
	private Expr e;

	public Attr(int l, int c, ID id, Expr e) {
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
