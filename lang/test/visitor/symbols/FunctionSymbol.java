package lang.test.visitor.symbols;

import java.util.List;

public class FunctionSymbol extends Symbol {
	private List<TypeSymbol> returnTypes; // Lista de tipos de retorno
	private List<VarSymbol> parameters; // Lista de parâmetros da função

	public FunctionSymbol(String name, List<TypeSymbol> returnTypes, List<VarSymbol> parameters) {
		super(name);
		this.returnTypes = returnTypes;
		this.parameters = parameters;
	}

	public List<TypeSymbol> getReturnTypes() {
		return returnTypes;
	}

	public List<VarSymbol> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		// Processar parâmetros
		StringBuilder paramStr = new StringBuilder();
		for (VarSymbol param : parameters) {
			paramStr.append(param.toString()).append(", ");
		}

		StringBuilder returnStr = new StringBuilder();
		for (TypeSymbol returnType : returnTypes) {
			returnStr.append(returnType.getName()).append(", ");
		}

		// Remover a última vírgula e espaço
		if (paramStr.length() > 0)
			paramStr.setLength(paramStr.length() - 2);
		if (returnStr.length() > 0)
			returnStr.setLength(returnStr.length() - 2);

		return "Function: " + name + "(" + paramStr.toString() + ") : [" + returnStr.toString() + "]";
	}
}