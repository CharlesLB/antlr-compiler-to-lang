/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.literals;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Essa classe representa um literal booleano.
 * 
 * @Expr true | false
 * 
 * @Example true
 * @Example false
 */
public class BoolLiteral extends Expr {
	private Boolean value;

	public BoolLiteral(int lin, int col, Boolean value) {
		super(lin, col);
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		return value ? true : false;
	}
}
