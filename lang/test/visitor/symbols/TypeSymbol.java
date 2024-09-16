package lang.test.visitor.symbols;

public class TypeSymbol extends Symbol {

	public TypeSymbol(String name) {
		super(name); // Nome do tipo, como "Int", "Float", etc.
	}

	@Override
	public String toString() {
		return "TypeSymbol: " + name;
	}
}
