package lang.ast.statements.data;

import java.util.HashMap;

import lang.ast.definitions.Data;
import lang.ast.expressions.ID;
import lang.ast.types.Btype;

/* Utilizada para representar estruturas do tipo:
 * 	x :: Int -> Decl.java
 * Pertencentes à Data ID -> Data.java
 * {
 * 	(...)
 * }
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