package lang.ast.lvalue;

import java.util.*;

import lang.ast.Node;
import lang.ast.definitions.Data;
import lang.symbols.DataTable;

/*Ex: person.name = "John"; */
public class FieldAccessLValue extends LValue {
	private LValue base;
	private IDLValue field;

	public FieldAccessLValue(int line, int pos, LValue base, IDLValue field) {
		super(line, pos);
		this.base = base;
		this.field = field;
	}

	public LValue getObject() {
		return base;
	}

	public IDLValue getField() {
		return field;
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		Object baseObject = base.interpret(context);

		System.out.println("-> " + baseObject + "   " + baseObject.getClass());

		// if (!(baseObject instanceof Data)) {
		// throw new RuntimeException("The base object is not a valid structure for
		// field access.");
		// }

		@SuppressWarnings("unchecked")
		HashMap<String, Object> objectMap = (HashMap<String, Object>) baseObject;

		for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
			System.out.println("Chave:: " + entry.getKey() + ", Valor: " + entry.getValue());
		}

		String fieldName = field.getName();

		if (!objectMap.containsKey(fieldName)) {
			throw new RuntimeException("Field '" + fieldName + "' does not exist in the object.");
		}

		Object fieldValue = objectMap.get(fieldName);

		if (fieldValue instanceof Node) {
			return ((Node) fieldValue).interpret(context);
		} else {
			// Se não for um Node, retorne o valor diretamente
			return fieldValue;
		}
	}
}