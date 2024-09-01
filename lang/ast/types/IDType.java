package lang.ast.types;

import java.util.HashMap;

import lang.ast.definitions.Type;

/**
 * Representa um tipo de dado.
 * 
 * @Parser type
 * 
 * @Example ID -> Vai contra a regra do Parser, mas funciona
 */
public class IDType extends Type {
	private String id;

	public IDType(int line, int column, String id) {
		super(line, column);
		this.id = id;
	}

	public String getName() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	public Object interpret(HashMap<String, Object> context) {
		System.out.println("Node IDType");
		return context.get(id);
	}
}