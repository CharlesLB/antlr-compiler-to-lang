package lang.ast.definitions;

import java.util.HashMap;

import lang.ast.Node;
import lang.ast.expressions.ID;
import lang.ast.statements.data.Decl;
import lang.symbols.DataTable;
import lang.symbols.FunctionTable;

/**
 * Representa a definição do parâmetro de uma função.
 * 
 * @Parser ID ‘::’ type {‘,’ ID ‘::’ type}
 * 
 * @Example a :: Int, b :: Int
 */
public class Param extends Node {
	private ID id;
	private Type t;

	public Param(int l, int c, ID id, Type type) {
		super(l, c);
		this.id = id;
		this.t = type;
	}

	public ID getID() {
		return id;
	}

	public Type getType() {
		return t;
	}

	// @Override
	// public String toString() {
	// return id.toString() + " :: " + t.toString();
	// }

	// @Override
	// public Object interpret(HashMap<String, Object> context) {
	// String paramName = id.getName();

	// System.out.println("--- Node Param --- " + paramName);

	// if (context.containsKey(paramName)) {
	// System.out.println("Saindo Node Param");
	// return context.get(paramName);
	// } else {
	// System.out.println("Else Node Param - " + id.getName() + " " + t);
	// context.put(id.getName(), t);
	// }

	// return context;
	// }
	@Override
	public Object interpret(HashMap<String, Object> context) {
		String paramName = id.getName();

		System.out.println("--- Node Param --- " + paramName);

		if (context.containsKey(paramName)) {
			System.out.println("Saindo Node Param");
			return context.get(paramName);
		}

		System.out.println("Else Node Param - " + id.getName() + "  " + t);

		Data data = DataTable.getInstance().getData(t.toString());

		if (data == null) {
			throw new RuntimeException("Estrutura de dados " + t.toString() + " não definida.");
		}

		// Inicializa a estrutura Data
		HashMap<String, Object> dataInstance = new HashMap<String, Object>();
		for (Decl decl : data.getDeclarations()) {
			dataInstance.put(decl.getID().getName(), decl.getType());
		}
		context.put(paramName, dataInstance);
		System.out.println("Estrutura de dados " + id.getName() + " inicializada.");

		return context.get(paramName);
	}
}
