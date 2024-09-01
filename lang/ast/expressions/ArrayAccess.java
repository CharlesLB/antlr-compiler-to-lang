/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.expressions;

import java.util.HashMap;
import java.util.List;

import lang.ast.definitions.Expr;

/**
 * Representa um acesso a um array.
 * 
 * @Parser exp '[' exp ']'
 * 
 * @Example array[0]
 */
public class ArrayAccess extends Expr {
	private Expr arrayExpr;
	private Expr indexExpr;

	public ArrayAccess(Expr arrayExpr, Expr indexExpr) {
		super(arrayExpr.getLine(), arrayExpr.getColumn());
		this.arrayExpr = arrayExpr;
		this.indexExpr = indexExpr;
	}

	public Expr getArray() {
		return arrayExpr;
	}

	public Expr getIndex() {
		return indexExpr;
	}

	@Override
	public String toString() {
		return arrayExpr.toString() + "[" + indexExpr.toString() + "]";
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		Object arrayValue = arrayExpr.interpret(context);

		if (arrayValue instanceof List) {
			List<?> arrayList = (List<?>) arrayValue;

			Object indexValue = indexExpr.interpret(context);
			if (!(indexValue instanceof Integer)) {
				throw new RuntimeException("ArrayList index must be an integer.");
			}

			int index = (Integer) indexValue;

			if (index < 0 || index >= arrayList.size()) {
				throw new RuntimeException("ArrayList index out of bounds.");
			}

			return arrayList.get(index);

		}

		/* Caso base onde retorno da função tem tamanho 1 */
		return arrayValue;
	}
}
