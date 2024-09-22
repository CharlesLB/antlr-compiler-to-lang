/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

public abstract class Symbol {
	protected String name;

	public Symbol(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Symbol{name='" + name + "'}";
	}
}
