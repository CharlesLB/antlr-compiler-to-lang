/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Representa um identificador.
 * 
 * @Parser ID
 * 
 * @Example x
 */
public class ID extends Expr {

	private String l;

	public ID(int l, int c, String name) {
		super(l, c);
		this.l = name;
	}

	public String getName() {
		return l;
	}

	// @Override
	public String toString() {
		return l;
	}

	public Object interpret(HashMap<String, Object> m) {
		return m.get(l);
	}
}