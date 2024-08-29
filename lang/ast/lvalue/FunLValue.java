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
		System.out.println("----- Entrando Função LVALUE: " + this.getFunctionName() + " ----");
		HashMap<String, Object> localContext = new HashMap<>(context);

		FunctionTable funAux = FunctionTable.getInstance();
		Fun function = funAux.getFunction(this.functionName.getName());
		if (function == null) {
			throw new RuntimeException("Função não definida: " + this.functionName.getName());
		}

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

		System.out.println("ReturnValue1: " + returnValue);

		// Captura os valores de retorno e os associa às variáveis de retorno
		// for (int i = 0; i < this.returnVars.size(); i++) {
		// String lvalueName = this.returnVars.get(i).toString(); // Supondo que LValue
		// tenha um método getName()
		// context.put(lvalueName, localContext.get(params.get(i).getID().getName()));
		// }

		System.out.println("ReturnValue2: " + returnValue);

		return returnValue;
	}
}