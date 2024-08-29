package lang.ast.lvalue;

import java.util.HashMap;

import lang.ast.expressions.Expr;

public abstract class LValue extends Expr {

	public LValue(int l, int c) {
		super(l, c);
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
