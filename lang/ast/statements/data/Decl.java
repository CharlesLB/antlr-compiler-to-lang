package lang.ast.statements.data;

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

	// // No interpretador, este Decl só precisa registrar o campo em uma estrutura
	// de Data
	// @Override
	// public void interpret(HashMap<String, Integer> context) {
	// // Esse nó Decl, no contexto da declaração de uma estrutura de dados, apenas
	// // define o campo
	// System.out.println("Field " + id.getName() + " with type " + t.toString() + "
	// declared.");
	// }
}