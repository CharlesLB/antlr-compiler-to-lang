package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Essa classe representa um literal float.
 * 
 * @Expr float
 * 
 * @Example 1.0
 * @Example 3.14
 */
public class FloatLiteral extends Expr {
	private float value;

	public FloatLiteral(int lin, int col, float value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		return value;
	}
}
