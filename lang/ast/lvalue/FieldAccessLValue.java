package lang.ast.lvalue;

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
}