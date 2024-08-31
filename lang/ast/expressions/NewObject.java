package lang.ast.expressions;

import java.util.HashMap;

import lang.ast.definitions.Data;
import lang.ast.definitions.Expr;
import lang.ast.definitions.Type;
import lang.ast.statements.data.Decl;
import lang.symbols.DataTable;

public class NewObject extends Expr {
	private Type type;

	public NewObject(int line, int column, Type type) {
		super(line, column);
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "new " + type.toString();
	}

	// @Override
	// public Object interpret(HashMap<String, Object> context) {
	// HashMap<String, Object> newObject = new HashMap<>();

	// System.out.println("Node NewObject NÃO IMPLEMENTADO ");

	// return newObject;
	// }
	@Override
	public Object interpret(HashMap<String, Object> context) {
		String typeName = this.getType().toString();

		Data dataDefinition = DataTable.getInstance().getData(typeName);
		if (dataDefinition == null) {
			throw new RuntimeException("O tipo '" + typeName + "' não foi definido.");
		}

		HashMap<String, Object> newObject = new HashMap<String, Object>();

		// // Inicialize os campos da nova estrutura com valores nulos
		for (Decl decl : dataDefinition.getDeclarations()) {
			String attrName = decl.getID().getName();

			Object initialValue = null;
			newObject.put(attrName, initialValue);

			System.out.println("Campo '" + attrName + "' inicializado com valor: " +
					initialValue);
		}

		// Retorne o novo objeto criado
		return newObject;
	}

	// Método auxiliar para obter o valor padrão para um tipo específico
	// private Object getDefaultValueForType(Type type) {
	// String typeName = type.getName();

	// switch (typeName) {
	// case "Int":
	// return 0;
	// case "Float":
	// return 0.0f;
	// case "Char":
	// return '\0';
	// case "Bool":
	// return false;
	// // Adicione outros tipos conforme necessário
	// default:
	// return null; // Para tipos complexos, inicialize como null ou recursivamente
	// como um novo
	// // objeto
	// }
	// }
}