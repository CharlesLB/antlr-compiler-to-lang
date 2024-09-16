/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.lvalue;

import java.util.*;

import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.test.visitor.Visitor;

/**
 * Representa um comando de atribuição.
 * 
 * @Parser LValue = Expr
 * 
 * @Example x = 10
 */
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

		if (id instanceof IDLValue) {
			IDLValue variable = (IDLValue) id;
			context.put(variable.getName(), exprObject);
		}

		if (id instanceof ArrayAccessLValue) {
			ArrayAccessLValue arrayAccess = (ArrayAccessLValue) id;

			Object arrayObject = arrayAccess.getArray().interpret(context);

			if (arrayObject instanceof Object[]) {
				Object[] array = (Object[]) arrayObject;
				Object indexValue = arrayAccess.getIndex().interpret(context);

				if (indexValue instanceof Integer) {
					int index = (Integer) indexValue;

					if (index >= 0 && index < array.length) {
						array[index] = new HashMap<String, Object>();
						array[index] = exprObject;

					} else {
						throw new RuntimeException("ArrayList index out of bounds.");
					}
				}
			}
		}

		if (id instanceof AttrAccessLValue) {
			AttrAccessLValue attrAccess = (AttrAccessLValue) id;

			Object objectObject = attrAccess.getObject().interpret(context);
			if (objectObject instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> subContext = (HashMap<String, Object>) objectObject;
				subContext.put(attrAccess.getAttr().getName(), exprObject);
				return exprObject;
			} else {
				throw new RuntimeException("The object is not a valid structure for attribute access.");
			}
		}

		return exprObject;

	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
