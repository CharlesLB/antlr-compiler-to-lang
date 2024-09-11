/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.statements.commands;

import java.util.List;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import visitors.Visitor;

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

	public Return(List<Expr> exprList) {
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

	public void accept(Visitor v) {
		v.visit(this);
	}
}
