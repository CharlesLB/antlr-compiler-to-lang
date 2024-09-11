/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um acesso a um array.
 *
 * @Parser exp '[' exp ']'
 *
 * @Example array[0] = 5;
 * @Example array[a] = array[b];
 */
public class ArrayAccessLValue extends LValue {
	private LValue array;
	private Expr index;

	public ArrayAccessLValue(LValue array, Expr index) {
		this.array = array;
		this.index = index;
	}

	public LValue getArray() {
		return array;
	}

	public Expr getIndex() {
		return index;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}