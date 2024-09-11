/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import lang.ast.definitions.Expr;
import lang.ast.definitions.Type;
import visitors.Visitor;

/**
 * Representa a criação de um novo objeto.
 *
 * @Parser new type
 *
 * @Example new Point
 */
public class NewObject extends Expr {
	private Type type;

	public NewObject(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "new " + type.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}