package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.BinOP;
import lang.ast.definitions.Expr;

/**
 * Essa classe representa a operação lógica AND entre duas expressões.
 * 
 * @Expr Expr && Expr
 * 
 * @Example true && false
 * @Example false && true
 * @Error Null && true -> Null values cannot be used in logical operations
 * @Error 1 && 2 -> Unsupported type for logical operation: java.lang.Integer
 */
public class And extends BinOP {
	public And(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return "(" + leftStr + " && " + rightStr + ")";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		boolean leftBool = toBoolean(leftValue);
		boolean rightBool = toBoolean(rightValue);

		Object aux = (leftBool && rightBool) ? true : false;
		System.out.println("--- Node And: " + aux);

		return (leftBool && rightBool) ? true : false;
	};

	private boolean toBoolean(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value == null) {
			throw new RuntimeException("Null values cannot be used in logical operations");
		} else {
			throw new RuntimeException("Unsupported type for logical operation: " + value.getClass().getName());
		}
	}
}