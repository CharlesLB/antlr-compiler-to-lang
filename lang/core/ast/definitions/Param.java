/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.definitions;

import java.util.HashMap;

import lang.core.ast.Node;
import lang.core.ast.expressions.ID;
import lang.core.ast.statements.data.Decl;
import lang.core.ast.symbols.DataTable;
import lang.core.ast.types.Btype;
import lang.core.ast.types.IDType;
import lang.core.ast.types.MatrixType;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	@Override
	public Object interpret(HashMap<String, Object> context) {
		String paramName = id.getName();

		if (context.containsKey(paramName)) {
			return context.get(paramName);
		}

		/* Verifica se o tipo é um Btype ou MatrixType de Btype */
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
					defaultValue = '\0'; /* Null character */
					break;
				case "Bool":
					defaultValue = false;
					break;
				default:
					throw new RuntimeException("Tipo básico desconhecido: " + btypeName);
			}

			context.put(paramName, defaultValue);
			return context.get(paramName);
		}
		/* Verifica se o tipo é um MatrixType de Data */
		else if (t instanceof MatrixType && ((MatrixType) t).getBaseType() instanceof IDType) {
			IDType baseType = (IDType) ((MatrixType) t).getBaseType();
			Data data = DataTable.getInstance().getData(baseType.toString());

			if (data == null) {
				throw new RuntimeException("Estrutura de dados " + baseType.toString() + " não definida.");
			}

			/* Inicializa uma matriz para o Data */
			int dimensions = ((MatrixType) t).getDimensions();
			Object[] dataMatrix = initializeDataMatrix(dimensions, data);
			context.put(paramName, dataMatrix);

			return context.get(paramName);
		}
		/* Verifica se o tipo é um Data comum */
		else {
			Data data = DataTable.getInstance().getData(t.toString());

			if (data == null) {
				throw new RuntimeException("Estrutura de dados " + t.toString() + " não definida.");
			}

			/** Inicializa a estrutura Data */
			HashMap<String, Object> dataInstance = new HashMap<String, Object>();
			for (Decl decl : data.getDeclarations()) {
				dataInstance.put(decl.getID().getName(), decl.getType());
			}
			context.put(paramName, dataInstance);

			return context.get(paramName);
		}
	}

	/** Método auxiliar para inicializar uma matriz de Data */
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

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
}