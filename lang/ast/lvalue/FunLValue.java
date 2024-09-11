/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.lvalue;

import java.util.*;
import java.util.stream.Collectors;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import lang.ast.expressions.ID;
import visitors.Visitor;

/**
 * Representa a chamada de uma função com variáveis de retorno.
 * 
 * @Parser ID ‘(’ [expr (‘,’ expr)*] ‘)’ [‘<’ lvalue {‘,’ lvalue} ‘>’] ‘;’
 * 
 * @Example sum(1, 2) <a, b>;
 */
public class FunLValue extends Cmd {
	private ID functionName;
	private List<Expr> arguments;
	private List<LValue> returnVars;

	public FunLValue(ID functionName, List<Expr> arguments, List<LValue> returnVars) {
		this.functionName = functionName;
		this.arguments = arguments;
		this.returnVars = returnVars;
	}

	public ID getFunctionName() {
		return functionName;
	}

	public List<Expr> getArguments() {
		return arguments;
	}

	public List<LValue> getLValues() {
		return returnVars;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(functionName.toString()).append("(");

		if (arguments != null && !arguments.isEmpty()) {
			sb.append(arguments.stream().map(Expr::toString).collect(Collectors.joining(", ")));
		}

		sb.append(")");

		if (returnVars != null && !returnVars.isEmpty()) {
			sb.append(" <").append(returnVars.stream().map(LValue::toString).collect(Collectors.joining(", ")))
					.append(">");
		}

		sb.append(";");
		return sb.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}