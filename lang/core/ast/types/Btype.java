/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.types;

import java.util.HashMap;

import lang.core.ast.definitions.Type;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	public Btype(int l, int c, String type) {
		super(l, c);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// @Override
	public String toString() {
		return type;
	}

	public Object interpret(HashMap<String, Object> context) {
		return context.get(type);
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