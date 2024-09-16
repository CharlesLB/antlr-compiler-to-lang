package lang.test.visitor;

import lang.core.ast.definitions.StmtList;
import lang.core.ast.Node;
import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.definitions.Type;
import lang.core.ast.expressions.ID;
import lang.core.ast.expressions.literals.BoolLiteral;
import lang.core.ast.expressions.literals.FloatLiteral;
import lang.core.ast.expressions.literals.IntLiteral;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.ReadLValue;
import lang.test.visitor.scope.Pair;
import lang.test.visitor.scope.ScopeTable;
import lang.test.visitor.symbols.Symbol;
import lang.test.visitor.symbols.TypeSymbol;
import lang.test.visitor.symbols.VarSymbol;

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

		List<Param> params = p.getParams();
		if (params != null && !params.isEmpty()) {
			System.out.println("Function parameters:");
			for (Param param : params) {
				param.accept(this);
			}
		} else {
			System.out.println("No parameters for function " + functionName + ".");
		}

		List<Type> returnTypes = p.getReturnTypes();
		if (returnTypes != null && !returnTypes.isEmpty()) {
			System.out.println("Return types:");
			for (Type returnType : returnTypes) {
				returnType.accept(this);
			}
		} else {
			System.out.println("Function " + functionName + " has no return types.");
		}

		List<Cmd> body = p.getBody();
		if (body != null && !body.isEmpty()) {
			System.out.println("Function body:");
			for (Cmd cmd : body) {
				System.out.println(cmd);
				cmd.accept(this);
			}
		} else {
			System.out.println("Function body is empty.");
		}
	}

	@Override
	public void visit(Assign assignment) {
		// Obtenha o identificador da variável e o valor
		ID variableName = assignment.getID();
		Expr value = assignment.getExp();

		// Imprimir para depuração
		System.out.println("Atribuição: " + variableName + " = " + value + " -> " + value.getClass());

		TypeSymbol typeSymbol = null;

		if (value instanceof IntLiteral) {
			typeSymbol = new TypeSymbol("Int"); // IntLiteral representa um inteiro
			System.out.println("INT");
		} else if (value instanceof FloatLiteral) {
			typeSymbol = new TypeSymbol("Float"); // FloatLiteral representa um float
		} else if (value instanceof BoolLiteral) {
			typeSymbol = new TypeSymbol("Bool"); // BoolLiteral representa um booleano
		} else {
			System.out.println("Tipo não reconhecido para a expressão: " + value);
			return;
		}

		VarSymbol symbol = new VarSymbol(variableName.toString(), typeSymbol, value);
		scopes.put(variableName.toString(), symbol);

		scopes.printScopes();
	}

	@Override
	public void visit(ReadLValue readLValue) {
		// Obter a variável a ser lida
		String variableName = readLValue.getLValue().toString();

		// Verificar se a variável está declarada no escopo

		Pair<Symbol, Integer> symbol = scopes.search(variableName);
		if (symbol == null) {
			// Reportar erro semântico: variável não declarada
			System.out.println("Erro semântico: variável '" + variableName + "' não foi declarada.");
		} else {
			// A variável está declarada, tudo certo
			System.out.println("Variável '" + variableName + "' está declarada.");
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
