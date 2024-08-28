package lang.ast;

import java.util.HashMap;

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
