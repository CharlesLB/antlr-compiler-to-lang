/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.operators;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Essa classe representa a operação de negação de uma expressão.
 * 
 * @Expr -Expr
 * 
 * @Example -2
 * @Example -1.0
 * @Error -"string" -> Unsupported type for negation
 * @Error -null -> Null value cannot be negated
 */
public class Neg extends Expr {
	private Expr expr;

	public Neg(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	public Expr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "-" + expr.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object value = expr.interpret(m);

		if (value == null) {
			throw new RuntimeException("Null value cannot be negated");
		}

		return -convertToIntOrFloat(value);
	}

	private float convertToIntOrFloat(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Float) {
			return (Float) value;
		} else {
			throw new RuntimeException("Unsupported type for negation: " + value.getClass().getName());
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
