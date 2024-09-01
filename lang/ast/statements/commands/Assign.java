package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import lang.ast.expressions.ID;

/**
 * Representa um comando de atribuição.
 * 
 * @Parser id ‘=’ exp ‘;’
 * 
 * @Example x = 1;
 * 
 * @Info O tipo de X NÃO é inferido.
 * 
 */
public class Assign extends Cmd {
	private ID id;
	private Expr e;

	public Assign(int l, int c, ID id, Expr e) {
		super(l, c);
		this.id = id;
		this.e = e;
	}

	public ID getID() {
		return id;
	}

	public Expr getExp() {
		return e;
	}

	public String toString() {
		return id.toString() + " = " + e.toString();
	}

	public Object interpret(HashMap<String, Object> context) {
		Object x = e.interpret(context);
		context.put(id.getName(), x);
		System.out.println("Node Assign: " + id.getName() + " => " + x);
		return x;
	}
}