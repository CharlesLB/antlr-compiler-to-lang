package lang.ast.definitions;

import java.util.HashMap;

import lang.ast.Node;
import lang.ast.expressions.ID;
import lang.ast.statements.data.Decl;
import lang.ast.types.Btype;
import lang.ast.types.IDType;
import lang.ast.types.MatrixType;
import lang.symbols.DataTable;

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

		// Verifica se o tipo é um Btype ou MatrixType de Btype
		if (t instanceof Btype || (t instanceof MatrixType && ((MatrixType) t).getBaseType() instanceof Btype)) {
			Object defaultValue = null;
			String btypeName = t instanceof Btype ? t.toString() : ((MatrixType) t).getBaseType().toString();

			switch (btypeName) {
				case "Int":
					defaultValue = 0;
					break;
				case "Float":
					defaultValue = 0.0f;
					break;
				case "Char":
					defaultValue = '\0'; // Null character
					break;
				case "Bool":
					defaultValue = false;
					break;
				default:
					throw new RuntimeException("Tipo básico desconhecido: " + btypeName);
			}

			context.put(paramName, defaultValue);
			System.out.println("Parametro básico " + id.getName() + " registrado com tipo " + t);
			return context.get(paramName);
		}
		// Verifica se o tipo é um MatrixType de Data
		else if (t instanceof MatrixType && ((MatrixType) t).getBaseType() instanceof IDType) {
			IDType baseType = (IDType) ((MatrixType) t).getBaseType();
			Data data = DataTable.getInstance().getData(baseType.toString());

			if (data == null) {
				throw new RuntimeException("Estrutura de dados " + baseType.toString() + " não definida.");
			}

			// Inicializa uma matriz de HashMap para o Data
			int dimensions = ((MatrixType) t).getDimensions();
			Object[] dataMatrix = initializeDataMatrix(dimensions, data);
			context.put(paramName, dataMatrix);
			System.out.println("Estrutura de dados " + id.getName() + " inicializada como matriz.");

			return context.get(paramName);
		}
		// Verifica se o tipo é um Data comum
		else {
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

	// Método auxiliar para inicializar uma matriz de Data
	private Object[] initializeDataMatrix(int dimensions, Data data) {
		if (dimensions == 1) {
			HashMap<String, Object>[] dataArray = new HashMap[data.getDeclarations().size()];
			for (int i = 0; i < dataArray.length; i++) {
				dataArray[i] = new HashMap<>();
				for (Decl decl : data.getDeclarations()) {
					dataArray[i].put(decl.getID().getName(), decl.getType());
				}
			}
			return dataArray;
		} else {
			Object[] outerArray = new Object[data.getDeclarations().size()];
			for (int i = 0; i < outerArray.length; i++) {
				outerArray[i] = initializeDataMatrix(dimensions - 1, data);
			}
			return outerArray;
		}
	}
}