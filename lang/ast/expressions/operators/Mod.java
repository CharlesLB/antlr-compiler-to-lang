/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions.operators;

import lang.ast.expressions.BinOP;
import visitors.Visitor;
import lang.ast.definitions.Expr;

/**
 * Essa classe representa a operação de módulo entre duas expressões.
 *
 * @Expr Expr % Expr
 *
 * @Example 2 % 1
 * @Example 1.0 % 2.0
 * @Error Int % Float -> Unsupported types for modulo
 * @Error Null % 1 -> Null values cannot be used in modulo
 */
public class Mod extends BinOP {
	public Mod(Expr l, Expr r) {
		super(l, r);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
