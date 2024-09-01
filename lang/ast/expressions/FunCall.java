package lang.ast.expressions;

import java.util.*;
import java.util.stream.*;

import lang.ast.definitions.Data;
import lang.ast.definitions.Expr;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.symbols.DataTable;
import lang.symbols.FunctionTable;

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

		HashMap<String, Object> localContext = new HashMap<>(context);

		// Cria uma lista de tipos de parâmetros para a função chamada
		List<String> argumentTypes = arguments.stream()
				.map(arg -> {
					Object value = arg.interpret(context);
					if (value instanceof Integer) {
						return "Int";
					} else if (value instanceof Float) {
						return "Float";
					} else if (value instanceof Boolean) {
						return "Bool";
					} else if (value instanceof Character) {
						return "Char";
					} else if (value instanceof Object[]) {
						Object[] array = (Object[]) value;
						if (array.length > 0 && array[0] instanceof HashMap) {
							HashMap<String, Object> firstElement = (HashMap<String, Object>) array[0];

							String identifiedType = identifyDataType(firstElement, DataTable.getInstance());
							return identifiedType + "[]";
						}
						return "Object[]"; // Caso contrário, trate como Object[] genérico
					} else if (value instanceof HashMap) {
						HashMap<String, Object> mapValue = (HashMap<String, Object>) value;

						// Identifica o tipo baseado nas chaves do HashMap
						return identifyDataType(mapValue, DataTable.getInstance());
					} else {
						throw new RuntimeException("Tipo de argumento não suportado: " + value.getClass().getSimpleName());
					}
				})
				.collect(Collectors.toList());

		Fun function = FunctionTable.getInstance().getFunction(this.functionName.getName(), argumentTypes);
		if (function == null) {
			throw new RuntimeException("Função não definida: " + this.functionName.getName() +
					" com tipos de argumentos: " + argumentTypes);
		}
		List<Param> params = function.getParams();

		if (params.size() != arguments.size()) {
			throw new RuntimeException(
					"Número de argumentos não corresponde ao número de parâmetros para a função: "
							+ this.functionName.getName());
		}

		for (int i = 0; i < params.size(); i++) {
			String paramName = params.get(i).getID().getName();
			Object argValue = arguments.get(i).interpret(context);
			localContext.put(paramName, argValue);
		}

		Object returnValue = function.interpret(localContext);

		if (returnValue instanceof List<?>) {
			List<?> returnList = (List<?>) returnValue;
			return returnList;
		} else {
			return returnValue;
		}
	}

	private String identifyDataType(HashMap<String, Object> element, DataTable dataTable) {
		for (Map.Entry<String, Data> entry : dataTable.getDataMap().entrySet()) {
			Data dataType = entry.getValue();
			Set<String> expectedKeys = dataType.getAttributes();

			if (element.keySet().equals(expectedKeys)) {
				return entry.getKey();
			}
		}

		// Se o tipo não for identificado, retorne "HashMap" como padrão --> Erro
		return "HashMap";
	}
}