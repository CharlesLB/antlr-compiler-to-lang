/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.literals;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;

/**
 * Essa classe representa um literal float.
 * 
 * @Expr float
 * 
 * @Example 1.0
 * @Example 3.14
 */
public class FloatLiteral extends Expr {
	private float value;

	public FloatLiteral(int lin, int col, float value) {
		super(lin, col);
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		return value;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
