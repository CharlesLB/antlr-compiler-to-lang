package lang.test.visitor.symbols;

public class CommandSymbol extends Symbol {

	public CommandSymbol(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "Command: " + name;
	}
}