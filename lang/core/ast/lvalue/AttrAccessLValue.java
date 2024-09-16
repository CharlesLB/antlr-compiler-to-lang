/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.lvalue;

import java.util.*;

import lang.core.ast.Node;
import lang.test.visitor.Visitor;

/**
 * Representa um acesso a um atributo de um objeto.
 * 
 * @Parser lvalue ‘.’ ID
 * 
 * @Example person.name
 */
public class AttrAccessLValue extends LValue {
	private LValue object;
	private IDLValue attr;

	public AttrAccessLValue(int line, int pos, LValue object, IDLValue attr) {
		super(line, pos);
		this.object = object;
		this.attr = attr;
	}

	public LValue getObject() {
		return object;
	}

	public IDLValue getAttr() {
		return attr;
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		Object objectObject = object.interpret(context);

		if (!(objectObject instanceof HashMap)) {
			throw new RuntimeException("The object object is not a valid structure for attr access.");
		}

		@SuppressWarnings("unchecked")
		HashMap<String, Object> objectMap = (HashMap<String, Object>) objectObject;

		String attrName = attr.getName();

		if (!objectMap.containsKey(attrName)) {
			throw new RuntimeException("Attr '" + attrName + "' does not exist in the object.");
		}

		Object attrValue = objectMap.get(attrName);

		if (attrValue instanceof Node) {
			return ((Node) attrValue).interpret(context);
		} else {
			/* Se não for um Node, retorne o valor diretamente */
			return attrValue;
		}
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}