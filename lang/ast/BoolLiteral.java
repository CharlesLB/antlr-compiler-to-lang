package lang.ast;

import java.util.HashMap;

public class BoolLiteral extends Expr {

	private boolean value;

	public BoolLiteral(int lin, int col, boolean value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		return value ? 1 : 0; // Retorna 1 para true, 0 para false
	}
}
