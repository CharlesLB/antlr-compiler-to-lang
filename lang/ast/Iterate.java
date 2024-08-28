package lang.ast;

import java.util.HashMap;

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
	public int interpret(HashMap<String, Integer> m) {
		int iterations = count.interpret(m);
		int lastResult = 0;
		for (int i = 0; i < iterations; i++) {
			lastResult = body.interpret(m); // Executa e salva o resultado da última iteração
		}
		return lastResult; // Retorna o resultado da última iteração
	}
}
