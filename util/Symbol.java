package util;

public class Symbol {

	public enum SymbolType {
		DATA, FUNCTION
	}

	private SymbolType type;
	private String name; // Nome do tipo de dado ou da função
	private Object definition; // Definição da função ou da estrutura de dados

	public Symbol(SymbolType type, String name, Object definition) {
		this.type = type;
		this.name = name;
		this.definition = definition;
	}

	public SymbolType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Object getDefinition() {
		return definition;
	}

	@Override
	public String toString() {
		return "Symbol [type=" + type + ", name=" + name + "]";
	}
}
