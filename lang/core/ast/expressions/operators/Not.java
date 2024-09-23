/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.operators;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Essa classe representa a operação de negação lógica de uma expressão.
 * 
 * @Expr !Expr
 * 
 * @Example !true
 * @Error !1 -> Unsupported type for logical negation: java.lang.Integer
 * @Error !null -> Null value cannot be negated
 */
public class Not extends Expr {

	private Expr expr;

	public Not(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	public Expr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "!" + expr.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object value = expr.interpret(m);

		if (value instanceof Boolean) {
			return ((Boolean) value) ? false : true;
		} else {
			throw new RuntimeException("Unsupported type for logical negation: " + value.getClass().getName());
		}
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
