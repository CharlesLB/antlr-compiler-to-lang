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
	public Object interpret(HashMap<String, Object> context) {
		Object countNumberIterations = count.interpret(context);

		System.out.println("--- Inicio Iteração: " + countNumberIterations);
		if (!(countNumberIterations instanceof Integer)) {
			throw new RuntimeException("Count expression must evaluate to an integer.");
		}

		int iterations = (Integer) countNumberIterations;
		Object lastResult = null;

		for (int i = 0; i < iterations; i++) {
			System.out.println("Iteração " + (i + 1) + " de " + iterations);
			lastResult = body.interpret(context);
		}

		System.out.println("--- Fim Iteração: " + lastResult);
		return lastResult;
	}
}
