/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

import java.util.Objects;

public class TypeSymbol extends Symbol {
	private TypeSymbol elementType; // Tipo base (Ponto)
	private int dimensions; // Número de dimensões (Ponto[])

	// Construtor para tipos básicos
	public TypeSymbol(String name) {
		super(name);
		this.elementType = null;
		this.dimensions = 0; // Tipo básico, sem dimensões de array
	}

	// Construtor para tipos de arrays
	public TypeSymbol(TypeSymbol baseType, int dimensions) {
		super(baseType.getName()); // Nome do tipo base
		this.elementType = baseType;
		this.dimensions = dimensions; // Quantidade de dimensões (ex.: Ponto[])
	}

	// Verifica se o tipo é um array
	public boolean isArray() {
		return this.dimensions > 0;
	}

	// Retorna o tipo base do array
	public TypeSymbol getElementType() {
		if (!isArray()) {
			throw new RuntimeException("Este tipo não é um array.");
		}
		return elementType;
	}

	// Retorna o número de dimensões do array
	public int getDimensions() {
		return dimensions;
	}

	@Override
	public String getName() {
		if (elementType != null) {
			// Se for um array, retorna o nome do tipo base com colchetes para cada dimensão
			StringBuilder nameBuilder = new StringBuilder(elementType.getName());
			for (int i = 0; i < dimensions; i++) {
				nameBuilder.append("[]");
			}
			return nameBuilder.toString();
		} else {
			// Se não for array, retorna o nome básico
			return name;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		TypeSymbol other = (TypeSymbol) obj;

		// Comparação do nome do tipo e das dimensões (caso seja um array)
		return name.equals(other.name) && dimensions == other.dimensions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, dimensions);
	}

	@Override
	public String toString() {
		return getName();
	}
}