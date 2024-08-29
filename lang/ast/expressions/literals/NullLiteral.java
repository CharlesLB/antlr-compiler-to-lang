package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

public class NullLiteral extends Expr {

	public NullLiteral(int lin, int col) {
		super(lin, col);
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		return 0; // Representa null como 0 na interpretação
	}
}
