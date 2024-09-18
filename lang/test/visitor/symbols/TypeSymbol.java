package lang.test.visitor.symbols;

public class TypeSymbol extends Symbol {
	private TypeSymbol elementType; // Usado para armazenar o tipo dos elementos, se for um array

	public TypeSymbol(String name) {
		super(name); // Nome do tipo, como "Int", "Float", etc.
		this.elementType = null;
	}

	// Construtor para arrays (onde 'elementType' indica o tipo dos elementos do
	// array)
	public TypeSymbol(String name, TypeSymbol elementType) {
		super(name);
		this.elementType = elementType;
	}

	public boolean isArray() {
		return elementType != null;
	}

	// Método para obter o tipo dos elementos do array
	public TypeSymbol getElementType() {
		if (!isArray()) {
			throw new RuntimeException("Este tipo não é um array.");
		}
		return elementType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
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
		return name + (isArray() ? "[]" : "");
	}
}
