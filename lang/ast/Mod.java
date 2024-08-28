package lang.ast;

import java.util.HashMap;

public class Mod extends BinOP {

	public Mod(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		return getLeft().toString() + " % " + getRight().toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		return getLeft().interpret(m) % getRight().interpret(m);
	}
}
