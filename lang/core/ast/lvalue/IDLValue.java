/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.lvalue;

import java.util.*;

import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

/**
 * Representa um identificador quando há necessidade de um LValue.
 * 
 * @Parser ID
 * 
 * @Example x
 */
public class IDLValue extends LValue {
	private String name;

	public IDLValue(int line, int pos, String name) {
		super(line, pos);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Object interpret(HashMap<String, Object> context) {
		if (!context.containsKey(name)) {
			throw new RuntimeException("Variable " + name + " is not defined.");
		}

		return context.get(name);
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