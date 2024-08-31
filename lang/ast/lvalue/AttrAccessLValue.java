package lang.ast.lvalue;

import java.util.*;

import lang.ast.Node;
import lang.ast.definitions.Data;
import lang.symbols.DataTable;

/*Ex: person.name = "John"; */
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

		System.out.println("Entrando AttrAcessLValue");

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

		System.out.println("Saindo AttrAcessLValue");

		if (attrValue instanceof Node) {
			return ((Node) attrValue).interpret(context);
		} else {
			// Se não for um Node, retorne o valor diretamente
			return attrValue;
		}
	}
}