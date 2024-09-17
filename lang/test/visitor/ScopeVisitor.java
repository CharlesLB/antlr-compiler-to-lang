package lang.test.visitor;

import lang.core.ast.definitions.StmtList;
import lang.core.ast.Node;
import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.definitions.Type;
import lang.core.ast.expressions.ArrayAccess;
import lang.core.ast.expressions.ID;
import lang.core.ast.expressions.literals.BoolLiteral;
import lang.core.ast.expressions.literals.CharLiteral;
import lang.core.ast.expressions.literals.FloatLiteral;
import lang.core.ast.expressions.literals.IntLiteral;
import lang.core.ast.expressions.literals.NullLiteral;
import lang.core.ast.lvalue.IDLValue;
import lang.core.ast.lvalue.LValue;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.Print;
import lang.core.ast.statements.commands.ReadLValue;
import lang.test.visitor.scope.Pair;
import lang.test.visitor.scope.ScopeTable;
import lang.test.visitor.symbols.Symbol;
import lang.test.visitor.symbols.TypeSymbol;
import lang.test.visitor.symbols.VarSymbol;
import lang.utils.TypeMismatchException;

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
	public void visit(Print print) {
		Expr expr = print.getExpr();

		TypeSymbol typeSymbol = null;

		if (expr instanceof IntLiteral) {
			typeSymbol = new TypeSymbol("Int");
		} else if (expr instanceof FloatLiteral) {
			typeSymbol = new TypeSymbol("Float");
		} else if (expr instanceof BoolLiteral) {
			typeSymbol = new TypeSymbol("Bool");
		} else if (expr instanceof CharLiteral) {
			typeSymbol = new TypeSymbol("Char");
		} else {
			// Depois verificar tipo data
			System.out.println("Tipo não reconhecido para a expressão: " + expr);
			return;
		}

		if (expr instanceof ID) {
			String variableName = expr.toString(); // Supondo que o ID tenha um método toString() ou getName()
			Pair<Symbol, Integer> symbol = scopes.search(variableName);

			if (symbol == null) {
				System.out.println("Erro semântico: variável '" + variableName + "' não foi declarada.");
			} else {
				System.out.println("Variável '" + variableName + "' está declarada.");
			}
		}

		if (typeSymbol.getName().equals("Int") || typeSymbol.getName().equals("Float")
				|| typeSymbol.getName().equals("Char")) {
			// Tipo válido para impressão
			System.out.println("Expressão de tipo '" + typeSymbol.getName() + "' é válida para o comando print.");
		} else {
			// Tipo inválido para o comando print
			System.out.println("Erro semântico: tipo '" + typeSymbol.getName() + "' inválido para o comando print.");
		}
	}

	public TypeSymbol visitLValue(LValue lvalue, Expr expr) {
		if (lvalue instanceof IDLValue) {
			// Caso 1: lvalue é um ID simples
			String variableName = ((IDLValue) lvalue).getName();

			Pair<Symbol, Integer> symbol = scopes.search(variableName);

			// Se a variável não foi encontrada no escopo, infere o tipo e a insere
			if (symbol == null) {
				TypeSymbol inferredType = visitExp(expr);

				System.out.println("Inferindo tipo da variável '" + variableName + "' como " + inferredType);

				VarSymbol newSymbol = new VarSymbol(variableName, inferredType, expr);
				scopes.put(variableName, newSymbol);

				return inferredType;
			}

			VarSymbol varSymbol = (VarSymbol) symbol.first();
			TypeSymbol varType = varSymbol.getType();

			System.out.println("Tipo da variável '" + variableName + "' é: " + varType);
			return varType;

			// } else if (lvalue instanceof ArrayAccess) {
			// // Caso 2: lvalue é um acesso a array (lvalue[exp])
			// ArrayAccess arrayAccess = (ArrayAccess) lvalue;
			// LValue array = arrayAccess.getArray(); // Pega o array
			// Expression index = arrayAccess.getIndex(); // Pega o índice

			// // Verifica o tipo do array
			// TypeSymbol arrayType = visitLValue(array);
			// if (!arrayType.isArray()) {
			// throw new RuntimeException("Erro semântico: o tipo " + arrayType + " não é um
			// array.");
			// }

			// // Verifica se o índice é um inteiro
			// TypeSymbol indexType = visitExp(index);
			// if (!indexType.equals(new TypeSymbol("Int"))) {
			// throw new RuntimeException("Erro semântico: índice do array deve ser do tipo
			// Int.");
			// }

			// // O tipo do lvalue será o tipo dos elementos do array
			// return arrayType.getElementType();

			// } else if (lvalue instanceof FieldAccess) {
			// // Caso 3: lvalue é um acesso a campo (lvalue.ID)
			// FieldAccess fieldAccess = (FieldAccess) lvalue;
			// LValue object = fieldAccess.getObject(); // Pega o objeto ou estrutura
			// ID field = fieldAccess.getField(); // Pega o campo sendo acessado

			// // Verifica o tipo do objeto
			// TypeSymbol objectType = visitLValue(object);
			// if (!objectType.isStruct() && !objectType.isObject()) {
			// throw new RuntimeException("Erro semântico: o tipo " + objectType + " não é
			// um objeto ou estrutura.");
			// }

			// // Verificar se o campo existe no objeto ou estrutura
			// TypeSymbol fieldType = objectType.getFieldType(field.getName());
			// if (fieldType == null) {
			// throw new RuntimeException("Erro semântico: o campo '" + field.getName() + "'
			// não existe em " + objectType);
			// }

			// return fieldType;

		} else {
			throw new RuntimeException("Erro semântico: tipo de lvalue desconhecido.");
		}
	}

	public TypeSymbol visitExp(Expr exp) {
		TypeSymbol exprType;

		// Verificar o tipo da expressão com instanceof
		if (exp instanceof IntLiteral) {
			exprType = new TypeSymbol("Int");
		} else if (exp instanceof FloatLiteral) {
			exprType = new TypeSymbol("Float");
		} else if (exp instanceof CharLiteral) {
			exprType = new TypeSymbol("Char");
		} else if (exp instanceof IDLValue) {
			// Para IDs, verificamos se a variável já foi declarada e pegamos seu tipo
			exprType = visitLValue((IDLValue) exp, exp);
		} else {
			throw new RuntimeException("Erro semântico: tipo de expressão desconhecido.");
		}

		System.out.println("Tipo da expressão é: " + exprType);
		return exprType; // Retorna o tipo da expressão
	}

	@Override
	public void visit(Assign assignment) {
		System.out.println("Atribuição: " + assignment.getID().getName() + " = " + assignment.getExp());
		System.out.println("Atribuição: " + assignment.getID().getClass() + " = " + assignment.getExp().getClass());

		IDLValue variable = assignment.getID();
		Expr expr = assignment.getExp();

		// Visita o lado esquerdo (lv)
		TypeSymbol varType = visitLValue(variable, expr);

		// Visita o lado direito (e)
		TypeSymbol exprType = visitExp(expr);

		// Verifica se os tipos são compatíveis
		if (!varType.equals(exprType)) {
			System.out.println(varType + " = " + exprType);
			throw new TypeMismatchException("Tipo incompatível na atribuição: "
					+ varType + " != " + exprType);
		}

		// Se tudo correto, atualiza a tabela de símbolos
		VarSymbol symbol = new VarSymbol(variable.getName(), varType, expr);
		scopes.put(variable.getName(), symbol);

		scopes.printScopes();
	}

	// @Override
	// public void visit(ReadLValue readLValue) {
	// String variableName = readLValue.getLValue().toString();

	// Pair<Symbol, Integer> symbol = scopes.search(variableName);
	// if (symbol == null) {
	// System.out.println("Erro semântico: variável '" + variableName + "' não foi
	// declarada.");
	// } else {
	// System.out.println("Variável '" + variableName + "' está declarada.");
	// }
	// }

	public void visit(Node node) {
		System.out.println("Visiting node at line: " + node.getLine() + ", column: " + node.getColumn());

		if (node instanceof Fun) {
			visit((Fun) node);
		} else {
			System.out.println("Visiting a generic node.");
		}
	}

	/* TIPOS */
	public TypeSymbol visit(IntLiteral intLiteral) {
		System.out.println("Constante inteira encontrada: " + intLiteral.getValue());
		return new TypeSymbol("Int");
	}

	public TypeSymbol visit(FloatLiteral floatLiteral) {
		System.out.println("Constante float encontrada: " + floatLiteral.getValue());
		return new TypeSymbol("Float");
	}

	public TypeSymbol visit(CharLiteral charLiteral) {
		System.out.println("Constante de caractere encontrada: " + charLiteral.getValue());
		return new TypeSymbol("Char");
	}

	public TypeSymbol visit(BoolLiteral boolLiteral) {
		System.out.println("Constante booleana encontrada: " + boolLiteral.getValue());
		return new TypeSymbol("Bool");
	}

	public TypeSymbol visit(NullLiteral nullLiteral) {
		System.out.println("Constante null encontrada.");
		return new TypeSymbol("Null");
	}

	/* Bases */
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
