package lang.ast;

/*Ex: arr[2] = 5; */
public class ArrayAccessLValue extends LValue {
	private LValue array;
	private Expr index;

	public ArrayAccessLValue(int line, int pos, LValue array, Expr index) {
		super(line, pos);
		this.array = array;
		this.index = index;
	}

	public LValue getArray() {
		return array;
	}

	public Expr getIndex() {
		return index;
	}
}