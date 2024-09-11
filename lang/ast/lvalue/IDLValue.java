/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import visitors.Visitor;

/**
 * Representa um identificador quando há necessidade de um LValue.
 *
 * @Parser ID
 *
 * @Example x
 */
public class IDLValue extends LValue {
	private String name;

	public IDLValue(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}