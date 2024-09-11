/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.types;

import lang.ast.definitions.Type;
import visitors.Visitor;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type ‘[’ ‘]’
 *         | btype
 * 
 * @Example Int[], Bool
 */
public class Btype extends Type {
	private String type;

	public Btype(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// @Override
	public String toString() {
		return type;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}