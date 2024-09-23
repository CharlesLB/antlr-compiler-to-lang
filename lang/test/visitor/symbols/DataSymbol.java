/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

import java.util.List;

public class DataSymbol extends Symbol {
	private List<VarSymbol> fields; // Campos declarados dentro do 'data'

	public DataSymbol(String name, List<VarSymbol> fields) {
		super(name);
		this.fields = fields;
	}

	public List<VarSymbol> getFields() {
		return fields;
	}

	public VarSymbol getField(String fieldName) {
		for (VarSymbol field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder fieldStr = new StringBuilder();
		for (VarSymbol field : fields) {
			fieldStr.append(field.toString()).append("; ");
		}
		return "Data: " + name + " {" + fieldStr.toString() + "}";
	}
}