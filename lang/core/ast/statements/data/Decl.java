/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.statements.data;

import java.util.HashMap;

import lang.core.ast.definitions.Data;
import lang.core.ast.expressions.ID;
import lang.core.ast.types.Btype;

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

	public Decl(int l, int c, ID id, Btype t) {
		super(l, c);
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

	@Override
	public Object interpret(HashMap<String, Object> context) {

		context.put(id.getName(), t);

		return context;
	}

}