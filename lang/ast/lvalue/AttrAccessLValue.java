/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import visitors.Visitor;

/**
 * Representa um acesso a um atributo de um objeto.
 * 
 * @Parser lvalue ‘.’ ID
 * 
 * @Example person.name
 */
public class AttrAccessLValue extends LValue {
	private LValue object;
	private IDLValue attr;

	public AttrAccessLValue(LValue object, IDLValue attr) {
		this.object = object;
		this.attr = attr;
	}

	public LValue getObject() {
		return object;
	}

	public IDLValue getAttr() {
		return attr;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}