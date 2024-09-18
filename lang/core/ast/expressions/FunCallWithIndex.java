/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions;

import java.util.*;
import java.util.stream.*;

import lang.core.ast.definitions.Expr;
import lang.core.ast.lvalue.IDLValue;
import lang.test.visitor.Visitor;

/**
 * Representa a chamada de uma função.
 * 
 * @Parser IDLValue ‘(’ [expr (‘,’ expr)*] ‘)’
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
		// Obtenha o contexto da função (localContext)
		HashMap<String, Object> localContext = new HashMap<>(context);

		// Interpretar os argumentos da função
		List<Object> argumentValues = new ArrayList<>();
		for (Expr arg : arguments) {
			argumentValues.add(arg.interpret(context));
		}

		// Chamar a função e obter o resultado
		Object functionResult = callFunction(functionName, argumentValues, localContext);

		// Tratar o acesso ao índice no valor retornado
		if (functionResult instanceof List<?>) {
			List<?> resultList = (List<?>) functionResult;

			// Interpretar o índice
			Object indexValue = indexExpr.interpret(context);
			if (!(indexValue instanceof Integer)) {
				throw new RuntimeException("O índice deve ser um número inteiro.");
			}

			int index = (Integer) indexValue;

			// Verificar se o índice está dentro dos limites da lista
			if (index < 0 || index >= resultList.size()) {
				throw new RuntimeException("Índice fora dos limites.");
			}

			// Retorna o valor correspondente ao índice
			return resultList.get(index);
		}

		throw new RuntimeException("A função não retornou uma lista ou array.");
	}

	private Object callFunction(IDLValue functionName, List<Object> argumentValues,
			HashMap<String, Object> localContext) {
		// Aqui você faria a lógica de chamada da função com base no nome e nos
		// argumentos
		// Exemplo:
		System.out.println("Chamando a função: " + functionName.getName() + " com argumentos " + argumentValues);

		// Simulação de um retorno da função (substitua pela lógica real)
		return Arrays.asList(10, 20, 30); // Exemplo: a função retorna uma lista de valores
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}