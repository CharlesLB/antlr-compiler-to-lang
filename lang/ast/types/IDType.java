/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.types;

import lang.ast.definitions.Type;
import visitors.Visitor;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type
 * 
 * @Example ID
 * 
 * @info não respeita perfeitamente a gramática, uma vez que existem 3
 *       categorias de ID e, na gramática, 2;
 */
public class IDType extends Type {
	private String id;

	public IDType(String id) {
		this.id = id;
	}

	public String getName() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}