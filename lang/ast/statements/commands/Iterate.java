package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

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
}
