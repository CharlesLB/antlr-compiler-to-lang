package lang.ast.expressions.operators;

import java.util.HashMap;

import lang.ast.definitions.Expr;

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
	public Object interpret(HashMap<String, Object> m) {
		Object value = expr.interpret(m);

		if (value instanceof Boolean) {
			return ((Boolean) value) ? 0 : 1; // Se o valor for verdadeiro, retorna 0 (falso); caso contrário, retorna 1
																				// (verdadeiro)
		} else {
			throw new RuntimeException("Unsupported type for logical negation: " + value.getClass().getName());
		}
	}
}
