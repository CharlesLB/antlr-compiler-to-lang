package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class CharLiteral extends Expr {

	private String value;

	public CharLiteral(int lin, int col, String value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		String strippedValue = value.substring(1, value.length() - 1);

		if (strippedValue.equals("\\n")) {
			return "\n";
		}
		return strippedValue;
	}
}
