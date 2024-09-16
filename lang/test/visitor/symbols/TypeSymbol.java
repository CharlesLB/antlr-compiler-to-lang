package lang.test.visitor.symbols;

public class TypeSymbol extends Symbol {
	private String type;

	public TypeSymbol(String name) {
		super(name);
		this.type = name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "TypeSymbol: " + name;
	}
}
