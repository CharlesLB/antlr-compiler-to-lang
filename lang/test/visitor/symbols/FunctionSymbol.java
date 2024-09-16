package lang.test.visitor.symbols;

import java.util.List;

public class FunctionSymbol extends Symbol {
	private TypeSymbol returnType;
	private List<VarSymbol> parameters; // Lista de parâmetros da função

	public FunctionSymbol(String name, TypeSymbol returnType, List<VarSymbol> parameters) {
		super(name);
		this.returnType = returnType;
		this.parameters = parameters;
	}

	public TypeSymbol getReturnType() {
		return returnType;
	}

	public List<VarSymbol> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		StringBuilder paramStr = new StringBuilder();
		for (VarSymbol param : parameters) {
			paramStr.append(param.toString()).append(", ");
		}
		return "Function: " + name + "(" + paramStr.toString() + ") : " + returnType.getName();
	}
}