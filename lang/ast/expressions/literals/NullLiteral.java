/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Essa classe representa um literal nulo.
 * 
 * @Expr null
 * 
 * @Example null
 * 
 * @Info Quando uma variável é declarada, ela é inicializada com null.
 */
public class NullLiteral extends Expr {
	public NullLiteral(int lin, int col) {
		super(lin, col);
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		return null;
	}
}
