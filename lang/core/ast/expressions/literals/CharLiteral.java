/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.literals;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Essa classe representa um literal caractere.
 * 
 * @Expr 'char'
 * 
 * @Example 'a'
 * @Example '\n'
 */
public class CharLiteral extends Expr {
	private String value;

	public CharLiteral(int lin, int col, String value) {
		super(lin, col);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "'" + value + "'";
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		String strippedValue = value.substring(1, value.length() - 1);

		if (strippedValue.equals("\\n")) {
			return "\n";
		}
		return strippedValue;
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
