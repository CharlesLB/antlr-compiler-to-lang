package lang.ast;

import java.util.HashMap;
import java.util.List;

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
			sb.append(params.toString());
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
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
