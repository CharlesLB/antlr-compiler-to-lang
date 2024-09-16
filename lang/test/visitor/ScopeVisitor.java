package lang.test.visitor;

import lang.core.ast.definitions.StmtList;
import lang.core.ast.Node;
import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.definitions.Type;
import lang.core.ast.expressions.ID;
import lang.test.visitor.scope.ScopeTable;

import java.util.List;

public class ScopeVisitor extends Visitor {

	private ScopeTable scopes;
	private int level;

	public ScopeVisitor() {
		scopes = new ScopeTable();
		level = scopes.getLevel();
	}

	@Override
	public void visit(Fun p) {
		ID functionName = p.getName();

		level = scopes.push();
		System.out.println("<<<<<<<<<< Função: " + functionName + " / " + level + " >>>>>>>>");

		// Visitando os parâmetros da função
		List<Param> params = p.getParams();
		if (params != null && !params.isEmpty()) {
			System.out.println("Function parameters:");
			for (Param param : params) {
				param.accept(this); // Visitando cada parâmetro
			}
		} else {
			System.out.println("No parameters for function " + functionName + ".");
		}

		// Visitando os tipos de retorno da função
		List<Type> returnTypes = p.getReturnTypes();
		if (returnTypes != null && !returnTypes.isEmpty()) {
			System.out.println("Return types:");
			for (Type returnType : returnTypes) {
				returnType.accept(this); // Visitando cada tipo de retorno
			}
		} else {
			System.out.println("Function " + functionName + " has no return types.");
		}

		// Visitando o corpo da função
		List<Cmd> body = p.getBody();
		if (body != null && !body.isEmpty()) {
			System.out.println("Function body:");
			for (Cmd cmd : body) {
				cmd.accept(this); // Visitando cada comando do corpo da função
			}
		} else {
			System.out.println("Function body is empty.");
		}
	}

	public void visit(Node node) {
		System.out.println("Visiting node at line: " + node.getLine() + ", column: " + node.getColumn());

		if (node instanceof Fun) {
			visit((Fun) node);
		} else {
			System.out.println("Visiting a generic node.");
		}
	}

	@Override
	public void visit(StmtList stmtList) {
		System.out.println("Visiting StmtList at line: " + stmtList.getLine() + ", column: " + stmtList.getColumn());

		Node cmd = stmtList.getCmd1();
		if (cmd != null) {
			cmd.accept(this); // Visitando cada comando na lista
		} else {
			System.out.println("StmtList is empty.");
		}
	}

	public void visit(Visitable p) {

	}
}
