package lang.ast.statements.commands;

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

/**
 * Representa um comando de impressão.
 * 
 * @Parser print exp ‘;’
 * 
 * @Example print 1;
 */
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
	public Object interpret(HashMap<String, Object> m) {
		Object value = expr.interpret(m);
		System.out.print(value); // Imprime o valor da expressão
		return value;
	}
}
