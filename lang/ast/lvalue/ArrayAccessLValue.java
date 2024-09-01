package lang.ast.lvalue;

import java.util.HashMap;

import lang.ast.definitions.Expr;

/**
 * Representa um acesso a um array.
 * 
 * @Parser exp '[' exp ']'
 * 
 * @Example array[0] = 5;
 * @Example array[a] = array[b];
 */
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

	@Override
	public Object interpret(HashMap<String, Object> context) {
		Object arrayValue = array.interpret(context);

		if (!(arrayValue instanceof Object[])) {
			throw new RuntimeException("The object is not a valid array.");
		}

		Object indexValue = index.interpret(context);
		if (!(indexValue instanceof Integer)) {
			throw new RuntimeException("The index is not an integer.");
		}

		int idx = (Integer) indexValue;

		// Forma de acesso ao elemento do array
		Object[] array = (Object[]) arrayValue;

		if (idx < 0 || idx >= array.length) {
			throw new RuntimeException("Array index out of bounds.");
		}

		return array[idx];
	}
}