/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.data;

import visitors.Visitor;

import lang.ast.definitions.Data;
import lang.ast.expressions.ID;
import lang.ast.types.Btype;

/**
 * Utilizada para representar estruturas do Data:
 *
 * @Parser ID ‘::’ type ‘;’
 *
 * @Example ID :: Int -> Decl.java
 *          Pertencentes à Data ID -> Data.java
 *          {
 *          (...)
 *          }
 */
public class Decl extends Data {
	private ID id;
	private Btype t;

	public Decl(ID id, Btype t) {
		this.id = id;
		this.t = t;
	}

	public ID getID() {
		return id;
	}

	public Btype getType() {
		return t;
	}

	public String toString() {
		return id.toString() + " :: " + t.toString() + ";";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

}