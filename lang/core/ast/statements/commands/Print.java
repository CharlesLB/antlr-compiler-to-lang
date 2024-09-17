/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.statements.commands;

import java.util.HashMap;

import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	public Expr getExpr() {
		return expr;
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

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}
