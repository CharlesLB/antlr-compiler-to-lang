package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.Expr;

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
