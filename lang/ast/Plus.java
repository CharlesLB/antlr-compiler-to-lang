package lang.ast;

/*
 * Esta classe representa uma expressão de soma.
 * Expr + Expr
 */

import java.util.HashMap;

public class Plus extends BinOP {

	public Plus(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	public String toString() {
		String s = getLeft().toString();
		if (getLeft() instanceof Plus) {
			s = "(" + s + ")";
		}
		return s + " + " + getRight().toString();
	}

	public int interpret(HashMap<String, Integer> m) {
		return getLeft().interpret(m) + getRight().interpret(m);
	}

}
