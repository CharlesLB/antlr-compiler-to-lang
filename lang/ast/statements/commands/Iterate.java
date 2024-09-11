/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa um comando de iteração.
 * 
 * @Parser iterate ‘(’ exp ‘)’ cmd
 * 
 * @Example iterate (10) { print 1; }
 */
public class Iterate extends Cmd {
	private Expr count;
	private Cmd body;

	public Iterate(Expr count, Cmd body) {
		this.count = count;
		this.body = body;
	}

	@Override
	public String toString() {
		return "iterate (" + count.toString() + ") " + body.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
