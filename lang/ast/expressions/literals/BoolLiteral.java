package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

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
	public Object interpret(HashMap<String, Object> m) {
		Object aux = value ? 1 : 0;
		System.out.println("Node BoolLiteral: " + aux.toString());
		return value ? 1 : 0; // Retorna 1 para true, 0 para false
	}
}
