package lang.ast.lvalue;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.ast.expressions.ID;
import lang.symbols.FunctionTable;

public class FunLValue extends Cmd {
	private ID functionName;
	private List<Expr> arguments;
	private List<LValue> returnVars;

	public FunLValue(int line, int column, ID functionName, List<Expr> arguments, List<LValue> returnVars) {
		super(line, column);
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

	@Override
	public Object interpret(HashMap<String, Object> context) {
		System.out.println("----- Entrando Função LVALUE: " + this.getFunctionName() + " ----");
		HashMap<String, Object> localContext = new HashMap<>(context);

		Fun function = FunctionTable.getInstance().getFunction(this.functionName.getName());
		if (function == null)
			throw new RuntimeException("Função não definida: " + this.functionName.getName());

		List<Param> params = function.getParams();

		if (params.size() != arguments.size()) {
			throw new RuntimeException(
					"Número de argumentos não corresponde ao número de parâmetros para a função: " + this.functionName.getName());
		}

		System.out.println("AAAAAA");

		for (int i = 0; i < params.size(); i++) {
			String paramName = params.get(i).getID().getName();
			Object argValue = arguments.get(i).interpret(context); // Interpreta valor do argumento da função
			localContext.put(paramName, argValue); // Associa o argumento ao parâmetro
			System.out.println(paramName + " = " + argValue);
		}

		System.out.println("BBBBBB");

		// Interpreta o corpo da função usando o contexto local
		Object returnValue = function.interpret(localContext);

		List<?> returnList = (List<?>) returnValue;

		if (returnVars != null && !returnVars.isEmpty()) {
			if (returnList.size() != returnVars.size()) {
				throw new RuntimeException(
						"O número de valores retornados não corresponde ao número de variáveis de retorno");
			}
			for (int i = 0; i < returnVars.size(); i++) {
				String lvalueName = returnVars.get(i).toString(); // Supondo que LValue tenha um método toString() ou
																													// getName()
				context.put(lvalueName, returnList.get(i));
			}
		}

		// System.out.println("ReturnValue1: " + returnValue);

		// Captura os valores de retorno e os associa às variáveis de retorno
		// if (returnVars != null && !returnVars.isEmpty()) {
		// System.out.println("=> " + returnVars);
		// for (int i = 0; i < returnVars.size(); i++) {
		// String lvalueName = returnVars.get(i).toString(); // Supondo que LValue tenha
		// um método toString() ou getName()
		// System.out.println("Dentro: " + lvalueName + " => " +
		// localContext.get(lvalueName));
		// context.put(lvalueName, localContext.get(lvalueName));
		// }
		// }

		// for (String key : context.keySet()) {
		// System.out.println(key + " = " + context.get(key));
		// }

		// System.out.println("ReturnValue2: " + returnValue);

		return context;
	}
}