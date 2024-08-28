package lang.ast.lvalue;

/*EX: x = 10; */
public class IDLValue extends LValue {
	String name;

	public IDLValue(int line, int pos, String name) {
		super(line, pos);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}