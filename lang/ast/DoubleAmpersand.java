package lang.ast;

import java.util.HashMap;

public class DoubleAmpersand extends BinOP {

	public DoubleAmpersand(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " && " + rightStr + ")";
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		int leftValue = getLeft().interpret(m);
		int rightValue = getRight().interpret(m);

		return (leftValue != 0 && rightValue != 0) ? 1 : 0;
	}
}