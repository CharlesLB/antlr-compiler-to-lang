package lang.ast.definitions;

import java.util.List;

import lang.ast.Node;
import lang.ast.expressions.ID;
import lang.ast.statements.data.Decl;
import lang.symbols.DataTable;

import java.util.HashMap;

/**
 * Utilizada para representar estruturas de objeto.
 * 
 * @Parser data ID '{' {decl} '}'
 * 
 * @Example data Point {
 *          x :: Int ; -> Essa é uma declaração de atributo
 *          }
 * 
 */
public class Data extends Node {
	private ID id;
	private List<Decl> declarations;

	public Data(int l, int c, ID id, List<Decl> declarations) {
		super(l, c);
		this.id = id;
		this.declarations = declarations;
	}

	public Data(int l, int c) {
		super(l, c);
	}

	public ID getID() {
		return id;
	}

	public List<Decl> getDeclarations() {
		return declarations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("data ").append(id.getName()).append(" {\n");
		for (Decl decl : declarations) {
			sb.append(" ").append(decl.toString()).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public Object interpret(HashMap<String, Object> context) {
		HashMap<String, Object> localContext = new HashMap<String, Object>();

		for (Decl decl : declarations) {
			localContext.put(decl.getID().getName(), decl.getType());
		}

		context.put(id.getName(), localContext);

		DataTable.getInstance().addData(this);
		System.out.println("Data structure " + id.getName() + " has been defined.");

		return null;
	}

}