package lang.ast;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FunCall extends Expr {
	private ID functionName;
	private List<Expr> arguments;

	public FunCall(int line, int column, ID functionName, List<Expr> arguments) {
		super(line, column);
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

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
