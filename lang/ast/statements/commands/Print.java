package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.expressions.Expr;

public class Print extends Cmd {

	private Expr expr;

	public Print(int lin, int col, Expr expr) {
		super(lin, col);
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "print " + expr.toString() + ";";
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		int value = expr.interpret(m);
		System.out.println(value); // Imprime o valor da expressão
		return value;
	}
}
