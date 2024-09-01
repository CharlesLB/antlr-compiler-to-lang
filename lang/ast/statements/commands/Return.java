/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

/**
 * Representa um comando de retorno.
 * 
 * @Parser return exp {‘,’ exp} ‘;’
 * 
 * @Example return 1;
 * @Example return 1, 2;
 */
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
	public Object interpret(HashMap<String, Object> m) {
		List<Object> returnValues = new ArrayList<>();

		for (Expr expr : exprList) {
			returnValues.add(expr.interpret(m));
		}

		if (returnValues.size() == 1) {
			return returnValues.get(0);
		}

		return returnValues;
	}
}
