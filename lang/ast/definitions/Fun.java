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

		HashMap<String, Object> localContext = new HashMap<String, Object>(context);

		if (params != null) {
			for (Param param : params) {
				// Verifica se o param existe no contexto da função
				Object argValue = param.interpret(localContext);
				// localContext.put(param.getID().getName(), argValue);
			}
		}

		// Executa o corpo da função
		Object returnValue = 0;
		for (Cmd cmd : body) {
			System.out.println("Executando comando: " + cmd);
			System.out.println("FICOU AQUI");
			returnValue = cmd.interpret(localContext); // Usa o contexto local

			// Condição para funções recursivas
			if (returnValue != null) {
				System.out.println("Retorno: " + returnValue);

				// Imprimir todos os comandos restantes
				for (Cmd remainingCmd : body.subList(body.indexOf(cmd) + 1, body.size())) {
					System.out.println("Comando restante: " + remainingCmd);
				}

				break;
			}
		}

		System.out.println("----- Saindo Função: " + this.getName() + " ---- ");

		return returnValue;

	}

}
