package lang.ast.lvalue;

/*
 * Esta classe representa um comando de atribuição.
 * LValue = Expr
 */

import java.util.HashMap;

import lang.ast.definitions.Cmd;
import lang.ast.definitions.Expr;

public class AssignLValue extends Cmd {

	private LValue id;
	private Expr e;

	public AssignLValue(int l, int c, LValue id, Expr e) {
		super(l, c);
		this.id = id;
		this.e = e;
	}

	public LValue getID() {
		return id;
	}

	public Expr getExp() {
		return e;
	}

	public String toString() {
		return id.toString() + " = " + e.toString();
	}

	public Object interpret(HashMap<String, Object> context) {
		Object exprObject = e.interpret(context);

		System.out.println("Node AssignLValue: " + id + " -- " + exprObject);

		if (id instanceof IDLValue) {
			System.out.println("IDLValue");
			IDLValue variable = (IDLValue) id;
			context.put(variable.getName(), exprObject);
		}

		if (id instanceof ArrayAccessLValue) {
			ArrayAccessLValue arrayAccess = (ArrayAccessLValue) id;

			Object arrayObject = arrayAccess.getArray().interpret(context);

			System.out.println(arrayObject);
			if (arrayObject instanceof Object[]) {
				Object[] array = (Object[]) arrayObject;
				Object indexValue = arrayAccess.getIndex().interpret(context);

				if (indexValue instanceof Integer) {
					int index = (Integer) indexValue;

					if (index >= 0 && index < array.length) {
						array[index] = exprObject;
						System.out.println("Sucess " + array[index]);
					}
				}
			}
		}

		if (id instanceof FieldAccessLValue) {
			FieldAccessLValue fieldAccess = (FieldAccessLValue) id;

			// Interpreta a estrutura base (ex: `x` em `x.x`)
			Object baseObject = fieldAccess.getField().interpret(context);

			System.out.println("> " + fieldAccess + " . " + baseObject);

			// if (!(baseObject instanceof HashMap)) {
			// throw new RuntimeException("O objeto base não é uma estrutura válida para
			// atribuição.");
			// }

			@SuppressWarnings("unchecked")
			HashMap<String, Object> objectMap = (HashMap<String, Object>) baseObject;

			String fieldName = fieldAccess.getField().getName();

			System.out.println("Assign Field: " + fieldName + " = " + exprObject);

			// Atribui o novo valor ao campo correspondente
			objectMap.put(fieldName, exprObject);

			return exprObject;
		}

		return exprObject;

	}
}
