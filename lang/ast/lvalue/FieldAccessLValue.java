package lang.ast.lvalue;

import java.util.HashMap;

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

		if (!(baseObject instanceof HashMap)) {
			throw new RuntimeException("The base object is not a valid structure for field access.");
		}

		@SuppressWarnings("unchecked")
		HashMap<String, Object> objectMap = (HashMap<String, Object>) baseObject;

		String fieldName = field.getName();

		if (!objectMap.containsKey(fieldName)) {
			throw new RuntimeException("Field '" + fieldName + "' does not exist in the object.");
		}

		System.out.println("Node FieldAccessValue: " + fieldName);

		return objectMap.get(fieldName);
	}
}