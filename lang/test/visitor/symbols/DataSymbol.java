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

	@Override
	public String toString() {
		StringBuilder fieldStr = new StringBuilder();
		for (VarSymbol field : fields) {
			fieldStr.append(field.toString()).append("; ");
		}
		return "Data: " + name + " {" + fieldStr.toString() + "}";
	}
}