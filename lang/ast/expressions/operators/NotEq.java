package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

public class NotEq extends BinOP {

	public NotEq(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return leftStr + " != " + rightStr;
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			throw new RuntimeException("Null values cannot be compared");
		}

		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			return !leftValue.equals(rightValue) ? 1 : 0;
		} else if (leftValue instanceof Double && rightValue instanceof Double) {
			return !leftValue.equals(rightValue) ? 1 : 0;
		} else if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
			return !leftValue.equals(rightValue) ? 1 : 0;
		} else if (leftValue instanceof Character && rightValue instanceof Character) {
			return !leftValue.equals(rightValue) ? 1 : 0;
		} else if (leftValue.equals(rightValue)) {
			return 0;
		} else {
			return 1;
		}
	}
}