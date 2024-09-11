/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import lang.ast.definitions.Expr;
import lang.ast.definitions.Type;
import visitors.Visitor;

/**
 * Representa a criação de um novo array.
 * 
 * @Parser new type '[' exp ']'
 * 
 * @Example new Int[10]
 */
public class NewArray extends Expr {
	private Type type;
	private Expr size;

	public NewArray(Type type, Expr size) {
		this.type = type;
		this.size = size;
	}

	public Type getType() {
		return type;
	}

	public Expr getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "new " + type.toString() + "[" + size.toString() + "]";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
