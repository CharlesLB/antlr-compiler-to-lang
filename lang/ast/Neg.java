package lang.ast;

import java.util.HashMap;

public class Neg extends Expr {

	private Expr expr;

	public Neg(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "-" + expr.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		return -expr.interpret(m); // Retorna o valor negado da expressão
	}
}
