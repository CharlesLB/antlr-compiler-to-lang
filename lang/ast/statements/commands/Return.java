package lang.ast.statements.commands;

import java.util.HashMap;
import java.util.List;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

public class Return extends Cmd {

	private List<Expr> exprList;

	public Return(int lin, int col, List<Expr> exprList) {
		super(lin, col);
		this.exprList = exprList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("return ");
		for (int i = 0; i < exprList.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append(exprList.get(i).toString());
		}
		sb.append(";");
		return sb.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> m) {
		// Interpretar todas as expressões na lista
		int lastValue = 0;
		for (Expr expr : exprList) {
			lastValue = expr.interpret(m);
		}
		return lastValue; // Retorna o valor da última expressão avaliada
	}
}
