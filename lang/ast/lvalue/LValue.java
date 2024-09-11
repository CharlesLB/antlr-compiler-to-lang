/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa variáveis que podem ter valor atribuido.
 *
 * @Parser LValue = Expr
 *
 * @Example x = 10
 */
public abstract class LValue extends Expr {
	public LValue() {

	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
