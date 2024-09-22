/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.symbols;

import java.util.List;

import lang.core.ast.definitions.Expr;

public class ObjectSymbol extends Symbol {
	private List<VarSymbol> fields; // Campos associados ao objeto

	public ObjectSymbol(String name, List<VarSymbol> fields) {
		super(name); // Chama o construtor da classe Symbol com o nome do objeto (ou tipo)
		this.fields = fields; // Inicializa os campos do objeto
	}

	// Retorna os campos do objeto
	public List<VarSymbol> getFields() {
		return fields;
	}

	// Busca por um campo específico pelo nome
	public VarSymbol getField(String fieldName) {
		for (VarSymbol field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null; // Retorna null se o campo não for encontrado
	}

	// Atualiza o valor de um campo
	public void setFieldValue(String fieldName, Expr value) {
		VarSymbol field = getField(fieldName);
		if (field != null) {
			field.setValue(value); // Atualiza o valor do campo
		} else {
			throw new RuntimeException("Erro: Campo '" + fieldName + "' não encontrado no objeto '" + getName() + "'.");
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ObjectSymbol{").append("name='").append(getName()).append("', fields={");
		for (VarSymbol field : fields) {
			sb.append(field.getName()).append(": ").append(field.getValue()).append(", ");
		}
		sb.append("}}");
		return sb.toString();
	}
}
