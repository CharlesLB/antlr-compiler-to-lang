package lang.test.visitor.symbols;

public class TypeSymbol extends Symbol {

	public TypeSymbol(String name) {
		super(name); // Nome do tipo, como "Int", "Float", etc.
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		TypeSymbol other = (TypeSymbol) obj;
		return name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "TypeSymbol: " + name;
	}
}
