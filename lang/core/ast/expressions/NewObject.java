/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.expressions;

import java.util.HashMap;

import lang.core.ast.definitions.Data;
import lang.core.ast.definitions.Expr;
import lang.core.ast.definitions.Type;
import lang.core.ast.statements.data.Decl;
import lang.core.ast.symbols.DataTable;
import lang.core.ast.types.Btype;
import lang.core.ast.types.MatrixType;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Representa a criação de um novo objeto.
 * 
 * @Parser new type
 * 
 * @Example new Point
 */
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

	@Override
	public Object interpret(HashMap<String, Object> context) {
		String typeName = this.getType().toString();

		if (type instanceof Btype
				|| (type instanceof MatrixType && ((MatrixType) type).getBaseType() instanceof Btype)) {
			Object defaultValue = null;
			String btypeName = type instanceof Btype ? type.toString() : ((MatrixType) type).getBaseType().toString();

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

			context.put(typeName, defaultValue);
			System.out.println("Parametro básico " + typeName + " registrado com tipo " + type);
			return context.get(typeName);
		} else {

			Data dataDefinition = DataTable.getInstance().getData(typeName);
			if (dataDefinition == null) {
				throw new RuntimeException("O tipo '" + typeName + "' não foi definido.");
			}

			HashMap<String, Object> newObject = new HashMap<String, Object>();

			/* Inicializa os campos da nova estrutura com valores nulos */
			for (Decl decl : dataDefinition.getDeclarations()) {
				String attrName = decl.getID().getName();

				Object initialValue = null;
				newObject.put(attrName, initialValue);
			}

			return newObject;
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