/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions;

import java.util.*;
import java.util.stream.*;

import lang.core.ast.definitions.Data;
import lang.core.ast.definitions.Expr;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.lvalue.IDLValue;
import lang.core.ast.symbols.DataTable;
import lang.core.ast.symbols.FunctionTable;
import lang.test.visitor.Visitor;

/**
 * Representa a chamada de uma função.
 * 
 * @Parser IDLValue ‘(’ [expr (‘,’ expr)*] ‘)’ [‘<’ lvalue {‘,’ lvalue} ‘>’] ‘;’
 * 
 * @Example sum(1, 2)
 * @Example sum(1+1)
 * @Example sum()
 * @Example sum(a, b)
 */
public class FunCallWithIndex extends Expr {
	private IDLValue functionName; // O nome da função
	private List<Expr> arguments; // Os argumentos da função
	private Expr indexExpr; // O índice para acessar o valor retornado

	public FunCallWithIndex(int line, int column, IDLValue functionName, List<Expr> arguments, Expr indexExpr) {
		super(line, column);
		this.functionName = functionName;
		this.arguments = arguments;
		this.indexExpr = indexExpr;
	}

	public IDLValue getFunctionName() {
		return functionName;
	}

	public List<Expr> getArguments() {
		return arguments;
	}

	public Expr getIndexExpr() {
		return indexExpr;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(functionName.toString()).append("(");

		if (arguments != null && !arguments.isEmpty()) {
			sb.append(arguments.stream().map(Expr::toString).collect(Collectors.joining(", ")));
		}

		sb.append(")").append("[").append(indexExpr.toString()).append("]");
		return sb.toString();
	}

	public Object interpret(HashMap<String, Object> context) {
		HashMap<String, Object> localContext = new HashMap<>(context);

		List<Object> argumentValues = new ArrayList<>();
		for (Expr arg : arguments) {
			argumentValues.add(arg.interpret(context));
		}

		Object functionResult = callFunction(functionName, argumentValues, localContext);

		if (functionResult instanceof List<?>) {
			List<?> resultList = (List<?>) functionResult;

			Object indexValue = indexExpr.interpret(context);
			if (!(indexValue instanceof Integer)) {
				throw new RuntimeException("O índice deve ser um número inteiro.");
			}

			int index = (Integer) indexValue;

			if (index < 0 || index >= resultList.size()) {
				throw new RuntimeException("Índice fora dos limites.");
			}

			return resultList.get(index);
		}

		throw new RuntimeException("A função não retornou uma lista ou array.");
	}

	private Object callFunction(IDLValue functionName, List<Object> argumentValues,
			HashMap<String, Object> context) {
		List<String> argumentTypes = arguments.stream()
				.<String>map(arg -> {
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
						return "Object[]";
					} else if (value instanceof HashMap) {
						HashMap<String, Object> mapValue = (HashMap<String, Object>) value;
						return identifyDataType(mapValue, DataTable.getInstance());
					} else {
						throw new RuntimeException("Tipo de argumento não suportado: " + value.getClass().getSimpleName());
					}
				})
				.collect(Collectors.toList());

		Fun function = FunctionTable.getInstance().getFunction(functionName.getName(), argumentTypes);
		if (function == null) {
			throw new RuntimeException("Função não definida: " + functionName.getName() +
					" com tipos de argumentos: " + argumentTypes);
		}

		List<Param> params = function.getParams();
		if (params.size() != argumentValues.size()) {
			throw new RuntimeException(
					"Número de argumentos não corresponde ao número de parâmetros para a função: "
							+ functionName.getName());
		}

		for (int i = 0; i < params.size(); i++) {
			String paramName = params.get(i).getID().getName();
			Object argValue = argumentValues.get(i);
			context.put(paramName, argValue);
		}

		return function.interpret(context);
	}

	private String identifyDataType(HashMap<String, Object> element, DataTable dataTable) {
		for (Map.Entry<String, Data> entry : dataTable.getDataMap().entrySet()) {
			Data dataType = entry.getValue();
			Set<String> expectedKeys = dataType.getAttributes();

			if (element.keySet().equals(expectedKeys)) {
				return entry.getKey();
			}
		}

		// Se o tipo não for identificado, retorne "HashMap" como padrão --> Indica Erro
		return "HashMap";
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}