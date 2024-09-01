package lang.ast.lvalue;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Representa um valor que pode ser atribuído.
 * 
 * @Parser LValue = Expr
 * 
 * @Example x = 10
 */
public abstract class LValue extends Expr {
	public LValue(int l, int c) {
		super(l, c);
	}

	public abstract Object interpret(HashMap<String, Object> context);
}
