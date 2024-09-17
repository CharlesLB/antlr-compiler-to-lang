/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.literals;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Essa classe representa um literal inteiro.
 * 
 * @Expr int
 * 
 * @Example 1
 * @Example 42
 * @Example -1
 */
public class IntLiteral extends Expr {
	private int value;

	public IntLiteral(int value, int c, int v) {
		super(value, c);
		this.value = v;
	}

	public int getValue() {
		return value;
	}

	// @Override
	public String toString() {
		return "" + value;
	}

	public Object interpret(HashMap<String, Object> m) {
		return value;
	}

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}