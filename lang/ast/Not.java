package lang.ast;

import java.util.HashMap;

public class Not extends Expr {

	private Expr expr;

	public Not(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "!" + expr.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		int value = expr.interpret(m);
		return (value == 0) ? 1 : 0; // Se o valor for 0 (falso), retorna 1 (verdadeiro), caso contrário, retorna 0
																	// (falso)
	}
}
