package lang.test.visitor.symbols;

public class VarSymbol extends Symbol {
	private TypeSymbol type;

	public VarSymbol(String name, TypeSymbol type) {
		super(name);
		this.type = type;
	}

	public TypeSymbol getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Variable: " + name + " : " + type.getName();
	}
}
