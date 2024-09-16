/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.statements.commands;

import java.util.HashMap;

import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.core.ast.expressions.ID;
import lang.test.visitor.Visitor;

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
		return x;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}