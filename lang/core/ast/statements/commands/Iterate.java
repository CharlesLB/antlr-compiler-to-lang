/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.statements.commands;

import java.util.HashMap;

import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	public Iterate(int lin, int col, Expr count, Cmd body) {
		super(lin, col);
		this.count = count;
		this.body = body;
	}

	public Expr getExpr() {
		return count;
	}

	public Cmd getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "iterate (" + count.toString() + ") " + body.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		Object countNumberIterations = count.interpret(context);

		if (!(countNumberIterations instanceof Integer)) {
			throw new RuntimeException("Count expression must evaluate to an integer.");
		}

		int iterations = (Integer) countNumberIterations;
		Object lastResult = null;

		for (int i = 0; i < iterations; i++) {
			lastResult = body.interpret(context);
		}

		return lastResult;
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
