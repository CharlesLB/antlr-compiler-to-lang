package lang.ast.definitions;

import java.util.HashMap;
import java.util.List;

import lang.ast.Node;
import lang.ast.expressions.ID;

/**
 * Representa a definição de uma função.
 * 
 * @Parser ID ‘(’ [params] ‘)’ [‘:’ type (‘,’ type)*] ‘{’ {cmd} ‘}’
 * 
 * @Example fun sum(a: Int, b: Int) : Int {}
 */
public class Fun extends Node {
	private ID name;
	private List<Param> params;
	private List<Type> returnTypes;
	private List<Cmd> body;

	public Fun(int l, int c, ID name, List<Param> params, List<Type> returnTypes, List<Cmd> body) {
		super(l, c);
		this.name = name;
		this.params = params;
		this.returnTypes = returnTypes;
		this.body = body;
	}

	public ID getName() {
		return name;
	}

	public List<Param> getParams() {
		return params;
	}

	public List<Type> getReturnTypes() {
		return returnTypes;
	}

	public List<Cmd> getBody() {
		return body;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name.toString()).append("(");

		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				sb.append(params.get(i).toString());
				if (i < params.size() - 1) {
					sb.append(", ");
				}
			}
		}

		sb.append(")");
		if (returnTypes != null && !returnTypes.isEmpty()) {
			sb.append(" : ");
			for (int i = 0; i < returnTypes.size(); i++) {
				sb.append(returnTypes.get(i).toString());
				if (i < returnTypes.size() - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append(" {\n");
		for (Cmd cmd : body) {
			sb.append(cmd.toString()).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		HashMap<String, Object> localContext = new HashMap<String, Object>(context);

		if (params != null) {
			for (Param param : params) {
				// Verifica se o param existe no contexto da função
				param.interpret(localContext);
			}
		}

		// Executa o corpo da função
		Object returnValue = 0;
		for (Cmd cmd : body) {
			returnValue = cmd.interpret(localContext);

			// Condição para funções recursivas
			if (returnValue != null) {
				break;
			}
		}

		return returnValue;
	}

}
