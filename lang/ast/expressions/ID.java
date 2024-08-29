package lang.ast.expressions;

/*
 * Esta classe representa um comando de Impressão.
 * Expr
 */

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class ID extends Expr {

	private String l;

	public ID(int l, int c, String name) {
		super(l, c);
		this.l = name;
	}

	public String getName() {
		return l;
	}

	// @Override
	public String toString() {
		return l;
	}

	public int interpret(HashMap<String, Integer> m) {
		return m.get(l);
	}
}