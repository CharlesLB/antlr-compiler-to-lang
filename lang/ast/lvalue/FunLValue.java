package lang.ast.lvalue;

import java.util.*;
import java.util.stream.Collectors;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Data;
import lang.ast.definitions.Expr;
import lang.ast.definitions.Fun;
import lang.ast.definitions.Param;
import lang.ast.expressions.ID;
import lang.symbols.DataTable;
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
		HashMap<String, Object> localContext = new HashMap<String, Object>(context);

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
						// Identifique o tipo baseado nas chaves do HashMap
						String identifiedType = identifyDataType(mapValue, DataTable.getInstance());
						return identifiedType;
					} else {
						System.out.println("-- " + value.getClass());
						throw new RuntimeException("Tipo de argumento não suportado: " + value.getClass().getSimpleName());
					}
				})
				.collect(Collectors.toList());

		// Busca a função correta na tabela de funções com base no nome e na assinatura
		Fun function = FunctionTable.getInstance().getFunction(this.functionName.getName(), argumentTypes);
		if (function == null) {
			throw new RuntimeException("Função não definida: " + this.functionName.getName() +
					" com tipos de argumentos: " + argumentTypes);
		}

		List<Param> params = function.getParams();

		if (params.size() != arguments.size()) {
			throw new RuntimeException(
					"Número de argumentos não corresponde ao número de parâmetros para a função: " + this.functionName.getName());
		}

		for (int i = 0; i < params.size(); i++) {
			String paramName = params.get(i).getID().getName();
			Object argValue = arguments.get(i).interpret(context); // Interpreta valor do argumento da função
			localContext.put(paramName, argValue); // Associa o argumento ao parâmetro
			System.out.println(paramName + " = " + argValue);
		}

		// Interpreta o corpo da função usando o contexto local
		Object returnValue = function.interpret(localContext);

		if (returnVars != null && !returnVars.isEmpty()) {
			if (returnVars.size() == 1) {
				// Se houver apenas uma variável de retorno, o valor deve ser diretamente
				// mapeado
				context.put(returnVars.get(0).toString(), returnValue);
			} else if (returnValue instanceof List<?>) {
				List<?> returnList = (List<?>) returnValue;
				if (returnList.size() != returnVars.size()) {
					throw new RuntimeException(
							"O número de valores retornados não corresponde ao número de variáveis de retorno");
				}

				for (int i = 0; i < returnVars.size(); i++) {
					String lvalueName = returnVars.get(i).toString();
					context.put(lvalueName, returnList.get(i));
				}
			} else {
				throw new RuntimeException(
						"O valor retornado não corresponde ao número de variáveis de retorno esperadas");
			}
		}

		return returnVars;
	}

	private String identifyDataType(HashMap<String, Object> element, DataTable dataTable) {
		System.out.println("--->" + element);
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