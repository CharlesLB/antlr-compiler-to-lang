package lang.ast.definitions;

import java.util.HashMap;
import java.util.List;

import lang.ast.Node;
import lang.ast.expressions.ID;

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
		System.out.println("----- Entrando Função: " + this.getName() + " ----");
		HashMap<String, Object> localContext = new HashMap<>(context);

		// Associa os parâmetros aos valores iniciais (deveriam ser passados na chamada)
		if (params != null) {
			for (Param param : params) {
				System.out.println("Params FUN: " + param.getID().getName());

				String paramName = param.getID().getName();

				if (!localContext.containsKey(paramName)) {
					throw new RuntimeException("Parametro " + paramName + " não tem valor definido.");
				}
				System.out.println("Parametro: " + paramName + " = " + localContext.get(paramName));

				Object argValue = param.interpret(context);
				context.put(param.getID().getName(), argValue);
			}
		}

		// Executa o corpo da função
		Object returnValue = 0;
		for (Cmd cmd : body) {
			returnValue = cmd.interpret(localContext); // Usa o contexto local
		}

		System.out.println("----- Saindo Função: " + this.getName() + " ---- ");

		return returnValue;

	}

}
