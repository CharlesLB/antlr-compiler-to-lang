/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions;

import java.util.HashMap;

import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}