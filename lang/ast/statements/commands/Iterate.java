package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

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
	public Object interpret(HashMap<String, Object> m) {
		Object iterationCount = count.interpret(m);
		int iterations;

		if (iterationCount instanceof Integer) {
			iterations = (Integer) iterationCount;
		} else {
			throw new RuntimeException("Iteration count must be an integer");
		}

		Object lastResult = null;

		for (int i = 0; i < iterations; i++) {
			lastResult = body.interpret(m);
		}

		return lastResult;
	}
}
