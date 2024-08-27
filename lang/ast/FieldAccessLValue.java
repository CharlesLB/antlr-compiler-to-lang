package lang.ast;

public class FieldAccessLValue extends LValue {
	LValue base;
	String field;

	public FieldAccessLValue(LValue base, String field, int line, int pos) {
		super(line, pos);
		this.base = base;
		this.field = field;
	}
}