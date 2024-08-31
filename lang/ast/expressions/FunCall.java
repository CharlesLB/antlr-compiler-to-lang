package lang.ast.expressions;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import lang.ast.definitions.Expr;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.symbols.FunctionTable;

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
	public Object interpret(HashMap<String, Object> context) {

		System.out.println("----- Entrando Função Call: " + this.functionName.getName() + " ----");
		HashMap<String, Object> localContext = new HashMap<>(context);

		Fun function = FunctionTable.getInstance().getFunction(this.functionName.getName());
		if (function == null) {
			throw new RuntimeException("Função não definida: " + this.functionName.getName());
		}

		List<Param> params = function.getParams();
		if (params.size() != arguments.size()) {
			throw new RuntimeException(
					"Número de argumentos não corresponde ao número de parâmetros para a função: " + this.functionName.getName());
		}

		for (int i = 0; i < params.size(); i++) {
			String paramName = params.get(i).getID().getName();
			Object argValue = arguments.get(i).interpret(context);
			localContext.put(paramName, argValue);
			System.out.println(paramName + " = " + argValue);
		}

		Object returnValue = function.interpret(localContext);

		if (returnValue instanceof List<?>) {
			List<?> returnList = (List<?>) returnValue;
			return returnList;
		} else {
			return returnValue;
		}
	}
}