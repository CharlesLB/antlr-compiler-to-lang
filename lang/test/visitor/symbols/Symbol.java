package lang.test.visitor.symbols;

public abstract class Symbol {
	protected String name;

	public Symbol(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
