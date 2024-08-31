package lang.ast.lvalue;

/*
 * Esta classe representa um comando de atribuição.
 * LValue = Expr
 */

import java.util.*;

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
					}
				}
			}
		}

		if (id instanceof AttrAccessLValue) {
			AttrAccessLValue attrAccess = (AttrAccessLValue) id;

			Object subContextObject = context.get(attrAccess.getObject().toString());
			HashMap<String, Object> subContext = (HashMap<String, Object>) subContextObject;

			// Atribui o novo valor ao campo correspondente
			subContext.put(attrAccess.getAttr().getName(), exprObject);

			return exprObject;
		}

		return exprObject;

	}
}
