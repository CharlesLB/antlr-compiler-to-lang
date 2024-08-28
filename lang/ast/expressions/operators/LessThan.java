package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.expressions.Expr;

public class LessThan extends BinOP {

	public LessThan(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		// Garante que os valores de left e right não são nulos
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " < " + rightStr + ")";
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		int leftValue = getLeft().interpret(m);
		int rightValue = getRight().interpret(m);
		return leftValue < rightValue ? 1 : 0;
	}
}
