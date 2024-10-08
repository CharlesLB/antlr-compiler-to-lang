/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.core.ast.definitions;

import java.util.*;

import lang.core.ast.Node;
import lang.core.ast.expressions.ID;
import lang.core.ast.statements.data.Decl;
import lang.test.visitor.Visitor;
import lang.utils.TypeMismatchException;

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

	public Set<String> getAttributes() {
		Set<String> attributes = new HashSet<>();
		for (Decl decl : declarations) {
			attributes.add(decl.getID().getName());
		}
		return attributes;
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

		return context;
	}

	public void accept(Visitor v) {
		try {
			v.visit(this);
		} catch (TypeMismatchException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

}