/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Representa variáveis que podem ter valor atribuido.
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
