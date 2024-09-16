/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions.operators;

import java.util.HashMap;

import lang.core.ast.expressions.BinOP;
import lang.test.visitor.Visitor;
import lang.core.ast.definitions.Expr;

/**
 * Essa classe representa a comparação de igualdade entre duas expressões.
 * 
 * @Expr Expr == Expr
 * 
 * @Example 2 == 1
 * @Example 1.0 == 2.0
 * @Example true == false
 * @Example 'a' == 'b'
 * @Example 'a' == null
 */
public class EQ extends BinOP {
	public EQ(int lin, int col, Expr l, Expr r) {
		super(lin, col, l, r);
	}

	@Override
	public String toString() {
		String leftStr = (getLeft() != null) ? getLeft().toString() : "null";
		String rightStr = (getRight() != null) ? getRight().toString() : "null";
		return leftStr + " == " + rightStr;
	}

	@Override
	public Object interpret(HashMap<String, Object> m) {
		Object leftValue = getLeft().interpret(m);
		Object rightValue = getRight().interpret(m);

		if (leftValue == null || rightValue == null) {
			return (leftValue == rightValue) ? true : false;
		}

		if (leftValue instanceof Integer && rightValue instanceof Integer) {
			return ((Integer) leftValue).intValue() == ((Integer) rightValue).intValue() ? true : false;
		}

		if (leftValue instanceof Float && rightValue instanceof Float) {
			return ((Float) leftValue).doubleValue() == ((Float) rightValue).doubleValue() ? true : false;
		}

		if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
			return ((Boolean) leftValue).booleanValue() == ((Boolean) rightValue).booleanValue() ? true : false;
		}

		if (leftValue instanceof Character && rightValue instanceof Character) {
			return ((Character) leftValue).charValue() == ((Character) rightValue).charValue() ? true : false;
		}

		/* Comparação para outros tipos usando equals */
		if (leftValue.equals(rightValue)) {
			return true;
		}

		/* Caso os valores não sejam iguais */
		return 0;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}