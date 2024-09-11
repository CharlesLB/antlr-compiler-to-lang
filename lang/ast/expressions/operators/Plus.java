/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.definitions.Expr;
import lang.ast.expressions.BinOP;
import visitors.Visitor;

/**
 * Essa classe representa a operação de soma de duas expressões.
 * 
 * @Expr Expr + Expr
 * 
 * @Example 2 + 1
 * @Example 1.0 + 2.0
 * @Error Int + Float -> Unsupported types for plus
 * @Error Null + 1 -> Null values cannot be used in plus
 */
public class Plus extends BinOP {

	public Plus(Expr l, Expr r) {
		super(l, r);
	}

	public String toString() {
		String s = getLeft().toString();
		if (getLeft() instanceof Plus) {
			s = "(" + s + ")";
		}
		return s + " + " + getRight().toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

}
