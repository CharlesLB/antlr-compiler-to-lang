package lang.ast;

public class ArrayAccessLValue extends LValue {
	LValue base;
	Expr index;

	public ArrayAccessLValue(LValue base, Expr index, int line, int pos) {
		super(line, pos);
		this.base = base;
		this.index = index;
	}
}