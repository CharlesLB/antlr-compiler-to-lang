package lang.ast.lvalue;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import lang.ast.expressions.ID;

public class FunLValue extends Cmd {
	private ID functionName;
	private List<Expr> arguments;
	private List<LValue> lvalues;

	public FunLValue(int line, int column, ID functionName, List<Expr> arguments, List<LValue> lvalues) {
		super(line, column);
		this.functionName = functionName;
		this.arguments = arguments;
		this.lvalues = lvalues;
	}

	public ID getFunctionName() {
		return functionName;
	}

	public List<Expr> getArguments() {
		return arguments;
	}

	public List<LValue> getLValues() {
		return lvalues;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(functionName.toString()).append("(");

		if (arguments != null && !arguments.isEmpty()) {
			sb.append(arguments.stream().map(Expr::toString).collect(Collectors.joining(", ")));
		}

		sb.append(")");

		if (lvalues != null && !lvalues.isEmpty()) {
			sb.append(" <").append(lvalues.stream().map(LValue::toString).collect(Collectors.joining(", ")))
					.append(">");
		}

		sb.append(";");
		return sb.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		return 1; // Tem que mudar
	}
}