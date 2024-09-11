/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import java.util.*;
import java.util.stream.*;

import lang.ast.definitions.Expr;
import visitors.Visitor;

/**
 * Representa a chamada de uma função.
 * 
 * @Parser ID ‘(’ [expr (‘,’ expr)*] ‘)’
 * 
 * @Example sum(1, 2)
 * @Example sum(1+1)
 * @Example sum()
 * @Example sum(a, b)
 */
public class FunCall extends Expr {
	private ID functionName;
	private List<Expr> arguments;

	public FunCall(ID functionName, List<Expr> arguments) {
		this.functionName = functionName;
		this.arguments = arguments;
	}

	public ID getFunctionName() {
		return functionName;
	}

	public List<Expr> getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(functionName.toString()).append("(");

		if (arguments != null && !arguments.isEmpty()) {
			sb.append(arguments.stream().map(Expr::toString).collect(Collectors.joining(", ")));
		}

		sb.append(")");
		return sb.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}