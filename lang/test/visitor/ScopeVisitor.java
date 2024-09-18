package lang.test.visitor;

import lang.core.ast.definitions.StmtList;
import lang.core.ast.definitions.Type;
import lang.core.ast.Node;
import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Expr;
import lang.core.ast.definitions.Fun;
import lang.core.ast.definitions.Param;
import lang.core.ast.expressions.BinOP;
import lang.core.ast.expressions.FunCallWithIndex;
import lang.core.ast.expressions.ID;
import lang.core.ast.expressions.literals.BoolLiteral;
import lang.core.ast.expressions.literals.CharLiteral;
import lang.core.ast.expressions.literals.FloatLiteral;
import lang.core.ast.expressions.literals.IntLiteral;
import lang.core.ast.expressions.literals.NullLiteral;
import lang.core.ast.expressions.operators.Not;
import lang.core.ast.expressions.operators.NotEq;
import lang.core.ast.expressions.operators.Plus;
import lang.core.ast.expressions.operators.And;
import lang.core.ast.expressions.operators.Div;
import lang.core.ast.expressions.operators.EQ;
import lang.core.ast.expressions.operators.LessThan;
import lang.core.ast.expressions.operators.Minus;
import lang.core.ast.expressions.operators.Mod;
import lang.core.ast.expressions.operators.Mul;
import lang.core.ast.expressions.operators.Neg;
import lang.core.ast.lvalue.ArrayAccessLValue;
import lang.core.ast.lvalue.IDLValue;
import lang.core.ast.lvalue.LValue;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.BlockCmd;
import lang.core.ast.statements.commands.If;
import lang.core.ast.statements.commands.Print;
import lang.core.ast.statements.commands.ReadLValue;
import lang.core.ast.statements.commands.Return;
import lang.core.ast.types.Btype;
import lang.core.ast.types.IDType;
import lang.test.visitor.scope.Pair;
import lang.test.visitor.scope.ScopeTable;
import lang.test.visitor.symbols.FunctionSymbol;
import lang.test.visitor.symbols.Symbol;
import lang.test.visitor.symbols.TypeSymbol;
import lang.test.visitor.symbols.VarSymbol;
import lang.utils.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class ScopeVisitor extends Visitor {

	private ScopeTable scopes;
	private int level;
	private Fun currentFunction;

	public ScopeVisitor() {
		scopes = new ScopeTable();
		level = scopes.getLevel();
		currentFunction = null;
	}

	public void visit(Fun p) {
		currentFunction = p;

		String functionName = p.getName().getName();

		if (level == 0) {
			List<TypeSymbol> returnTypeSymbol = new ArrayList<>();
			for (Type returnTytpe : p.getReturnTypes()) {
				TypeSymbol typeSymbol = visit(returnTytpe); // Converter o Type em TypeSymbol
				returnTypeSymbol.add(typeSymbol); // Adicionar o TypeSymbol à lista
			}

			List<VarSymbol> parameterSymbols = new ArrayList<>();
			System.out.println(p.getParams());
			if (p.getParams() != null) {
				for (Param param : p.getParams()) {
					System.out.println(param);
					String paramName = param.getID().getName(); // Obter o nome do parâmetro
					TypeSymbol paramType = visit(param.getType()); // Converter o Type em TypeSymbol
					VarSymbol varSymbol = new VarSymbol(paramName, paramType, null); // Criar o VarSymbol
					parameterSymbols.add(varSymbol); // Adicionar o VarSymbol à lista
				}
			}
			System.out.println("A");

			FunctionSymbol functionSymbol = new FunctionSymbol(functionName, returnTypeSymbol, parameterSymbols);
			scopes.put(functionName, functionSymbol); // Registrar a função no escopo global
			System.out.println("Função '" + functionName + "' registrada no escopo global.");
		}

		level = scopes.push();
		System.out.println("<<<<<<<<<< Função: " + functionName + " / " + level + " >>>>>>>>");

		List<Param> params = p.getParams();
		if (params != null && !params.isEmpty()) {
			System.out.println("Function parameters:");
			for (Param param : params) {
				System.out.println(param);
				visit(param);
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
				cmd.accept(this);
			}
		} else {
			System.out.println("Function body is empty.");
			System.out.println("Escopo desempilhado: Nível " + level);
		}

		level = scopes.pop();
	}

	public void visit(Param param) {
		String paramName = param.getID().getName();

		TypeSymbol paramType = visit(param.getType());
		System.out.println(paramType);

		if (scopes.search(paramName) != null) {
			throw new TypeMismatchException("Erro semântico: parâmetro '" + paramName + "' já foi declarado.");
		}

		VarSymbol paramSymbol = new VarSymbol(paramName, paramType, null);
		scopes.put(paramName, paramSymbol);

		System.out.println("Parâmetro '" + paramName + "' do tipo '" + paramType.getName() + "' declarado.");
	}

	public TypeSymbol visit(Type type) {
		if (type instanceof Btype) {
			String typeName = ((Btype) type).getType();
			return new TypeSymbol(typeName);
		} else {
			// Lidar com casos em que o tipo não é um IDType
			throw new TypeMismatchException("Erro semântico: tipo inválido.");
		}
	}

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

	public TypeSymbol visit(LValue lvalue, Expr expr) {
		if (lvalue instanceof IDLValue) {
			// Caso 1: lvalue é um ID simples
			String variableName = ((IDLValue) lvalue).getName();

			Pair<Symbol, Integer> symbol = scopes.search(variableName);

			// Se a variável não foi encontrada no escopo, infere o tipo e a insere
			if (symbol == null) {
				System.out.println("Expr: " + expr);
				TypeSymbol inferredType = visit(expr);

				System.out.println("Inferindo tipo da variável '" + variableName + "' como " + inferredType);

				VarSymbol newSymbol = new VarSymbol(variableName, inferredType, expr);
				scopes.put(variableName, newSymbol);

				return inferredType;
			} else if (lvalue instanceof ArrayAccessLValue) {
				// Caso 2: lvalue é um acesso a array (lvalue[exp])
				Expr array = ((ArrayAccessLValue) lvalue).getArray();
				Expr index = ((ArrayAccessLValue) lvalue).getIndex();

				// Verifica o tipo do array
				TypeSymbol arrayType = visit(array);
				if (!arrayType.isArray()) {
					throw new TypeMismatchException("Erro semântico: o tipo " + arrayType + " não é um array.");
				}

				// Verifica se o índice é um inteiro
				TypeSymbol indexType = visit(index);
				if (!indexType.equals(new TypeSymbol("Int"))) {
					throw new TypeMismatchException("Erro semântico: índice do array deve ser do tipo	Int.");
				}

				// O tipo do lvalue será o tipo dos elementos do array
				return arrayType.getElementType();

				// } else if (lvalue instanceof FieldAccess) {
				// // Caso 3: lvalue é um acesso a campo (lvalue.ID)
				// FieldAccess fieldAccess = (FieldAccess) lvalue;
				// LValue object = fieldAccess.getObject(); // Pega o objeto ou estrutura
				// ID field = fieldAccess.getField(); // Pega o campo sendo acessado

				// // Verifica o tipo do objeto
				// TypeSymbol objectType = visitLValue(object);
				// if (!objectType.isStruct() && !objectType.isObject()) {
				// throw new TypeMismatchException("Erro semântico: o tipo " + objectType + "
				// não é
				// um objeto ou estrutura.");
				// }

				// // Verificar se o campo existe no objeto ou estrutura
				// TypeSymbol fieldType = objectType.getFieldType(field.getName());
				// if (fieldType == null) {
				// throw new TypeMismatchException("Erro semântico: o campo '" + field.getName()
				// + "'
				// não existe em " + objectType);
				// }

				// return fieldType;

			} else {
				throw new TypeMismatchException("Erro semântico: tipo de lvalue desconhecido.");
			}
		} else {
			throw new TypeMismatchException("Erro semântico: tipo de lvalue desconhecido.");
		}
	}

	public TypeSymbol visit(IDLValue idlValue) {
		String variableName = ((IDLValue) idlValue).getName();

		Pair<Symbol, Integer> symbol = scopes.search(variableName);

		// Se a variável não foi encontrada no escopo, infere o tipo e a insere
		if (symbol == null) {
			System.out.println("Expr: ");
			TypeSymbol inferredType = visit(idlValue);
			return inferredType;
		}
		// Mudarr
		return new TypeSymbol("Int");
	}

	public TypeSymbol visit(Expr exp) {
		TypeSymbol exprType;

		System.out.println(exp + " - " + exp.getClass());

		if (exp instanceof BinOP) {
			exprType = visit((BinOP) exp);
		} else if (exp instanceof Not) {
			exprType = visit((Not) exp);
		} else if (exp instanceof Neg) {
			exprType = visit((Neg) exp);
		} else if (exp instanceof IntLiteral) {
			exprType = new TypeSymbol("Int");
		} else if (exp instanceof FloatLiteral) {
			exprType = new TypeSymbol("Float");
		} else if (exp instanceof CharLiteral) {
			exprType = new TypeSymbol("Char");
		} else if (exp instanceof IDLValue) {
			// Para IDs, verificamos se a variável já foi declarada e pegamos seu tipo
			exprType = visit((IDLValue) exp);
		} else if (exp instanceof FunCallWithIndex) {
			// Para IDs, verificamos se a variável já foi declarada e pegamos seu tipo
			exprType = visit((FunCallWithIndex) exp);
		} else {
			throw new TypeMismatchException("Erro semântico: tipo de expressão desconhecido.");
		}

		System.out.println("Tipo da expressão é: " + exprType);
		return exprType; // Retorna o tipo da expressão
	}

	public void visit(Assign assignment) {
		System.out.println("Atribuição: " + assignment.getID().getName() + " = " + assignment.getExp());
		System.out.println("Atribuição: " + assignment.getID().getClass() + " = " + assignment.getExp().getClass());

		IDLValue variable = assignment.getID();
		Expr expr = assignment.getExp();

		// Visita o lado esquerdo (lv)
		TypeSymbol varType = visit(variable, expr);

		System.out.println(varType);

		// Visita o lado direito (e)
		TypeSymbol exprType = visit(expr);

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

	public TypeSymbol visit(FunCallWithIndex funCallWithIndex) {
		System.out.println("Chamando Função: " + funCallWithIndex.getFunctionName().getName());

		String functionName = funCallWithIndex.getFunctionName().getName();
		Pair<Symbol, Integer> symbol = scopes.search(functionName);
		System.out.println(symbol);

		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: função '" + functionName + "' não foi declarada.");
		}
		FunctionSymbol functionSymbol = (FunctionSymbol) symbol.first();

		// Verificar os tipos dos argumentos
		List<Expr> arguments = funCallWithIndex.getArguments();
		List<VarSymbol> expectedParameters = functionSymbol.getParameters();

		if (arguments.size() != expectedParameters.size()) {
			throw new TypeMismatchException(
					"Erro semântico: número de argumentos incorreto para a função '" + functionName + "'.");
		}

		// Verificar o tipo de cada argumento
		for (int i = 0; i < arguments.size(); i++) {
			TypeSymbol argType = visit(arguments.get(i)); // Verificar o tipo do argumento
			if (!argType.equals(expectedParameters.get(i).getType())) {
				throw new TypeMismatchException("Erro semântico: tipo do argumento " + (i + 1) + " da função '"
						+ functionName + "' não corresponde. Esperado: " + expectedParameters.get(i).getType()
						+ ", Encontrado: " + argType);
			}
		}

		// Obter os tipos de retorno da função
		List<TypeSymbol> returnTypes = functionSymbol.getReturnTypes();

		// Verificar o tipo do índice e acessá-lo
		TypeSymbol indexType = visit(funCallWithIndex.getIndexExpr());
		if (!indexType.equals(new TypeSymbol("Int"))) {
			throw new TypeMismatchException("Erro semântico: o índice deve ser do tipo Int.");
		}

		// Verificar o índice dentro dos retornos
		int index = (int) funCallWithIndex.getIndexExpr().interpret(null); // Interpretar o índice
		System.out.println("INDEX: " + index);
		if (index >= returnTypes.size() || index < 0) {
			throw new TypeMismatchException("Erro semântico: índice de retorno fora dos limites.");
		}

		// Retornar o tipo correspondente ao índice
		return returnTypes.get(index);
	}

	public void visit(Return returnCmd) {
		if (currentFunction == null) {
			throw new TypeMismatchException("Erro semântico: comando 'return' fora de uma função.");
		}

		List<Type> expectedReturnTypes = currentFunction.getReturnTypes();
		List<Expr> returnExpressions = returnCmd.getExprList();

		// Verificar se o número de expressões no 'return' corresponde ao número de
		// tipos de retorno
		if (expectedReturnTypes.size() != returnExpressions.size()) {
			throw new TypeMismatchException("Erro semântico: Número incorreto de valores retornados.");
		}

		// Verificar cada expressão do 'return'
		for (int i = 0; i < returnExpressions.size(); i++) {
			Type expectedType = expectedReturnTypes.get(i);
			TypeSymbol returnTypeSymbol = visit(returnExpressions.get(i));

			// Verificar se o tipo da expressão retornada corresponde ao tipo esperado
			if (!returnTypeSymbol.equals(visit(expectedType))) {
				throw new TypeMismatchException("Erro semântico: Tipo incompatível no valor de retorno " + (i + 1) +
						". Esperado: " + expectedType + ", Encontrado: " + returnTypeSymbol);
			}
		}
	}

	public void visit(If ifStmt) {
		System.out.println("Visitando estrutura If no escopo " + level);

		// 1. Verificar a expressão condicional (exp) deve ser do tipo Bool
		TypeSymbol conditionType = visit(ifStmt.getExp()); // Visitar a expressão condicional
		if (!conditionType.equals(new TypeSymbol("Bool"))) {
			throw new RuntimeException("Erro semântico: A condição do 'if' deve ser do tipo Bool.");
		}
		System.out.println("Condição do 'if' verificada com sucesso. Tipo: Bool");

		// 2. Empilhar um novo escopo para o bloco 'then' (thn)
		level = scopes.push();
		System.out.println("Novo escopo empilhado para o bloco 'then': Nível " + level);

		// 3. Visitar o bloco 'then'
		if (ifStmt.getThenCmd() instanceof BlockCmd) {
			visit((BlockCmd) ifStmt.getThenCmd()); // Visitar o bloco de comandos
		} else {
			System.out.println("ESTTOUUUU");
			visit(ifStmt.getThenCmd()); // Caso seja um único comando, visitá-lo diretamente
		}

		// 4. Desempilhar o escopo do bloco 'then' após a visita
		level = scopes.pop();
		System.out.println("Escopo desempilhado após o bloco 'then': Nível " + level);

		// 5. Se houver um bloco 'else', visitá-lo
		if (ifStmt.getThenEls() != null) {
			// Empilhar um novo escopo para o bloco 'else' (els)
			level = scopes.push();
			System.out.println("Novo escopo empilhado para o bloco 'else': Nível " + level);

			// Verificar se o bloco 'else' é um BlockCmd ou um único comando
			if (ifStmt.getThenEls() instanceof BlockCmd) {
				visit((BlockCmd) ifStmt.getThenEls()); // Visitar o bloco de comandos
			} else {
				visit(ifStmt.getThenEls()); // Caso seja um único comando, visitá-lo diretamente
			}

			// Desempilhar o escopo do bloco 'else' após a visita
			level = scopes.pop();
			System.out.println("Escopo desempilhado após o bloco 'else': Nível " + level);
		}
	}

	public void visit(BlockCmd blockCmd) {
		System.out.println("Visitando bloco de comandos no escopo " + level);

		// Empilhar um novo escopo para o bloco de comandos
		level = scopes.push();
		System.out.println("Novo escopo empilhado para o bloco de comandos: Nível " + level);

		List<Cmd> commands = blockCmd.getCommands();

		// Processar o primeiro comando e obter os resultados (V1; Γ1)
		if (commands.isEmpty()) {
			throw new RuntimeException("Erro semântico: Bloco de comandos vazio.");
		}

		for (Cmd cmd : commands) {
			visit(cmd);
		}

		level = scopes.pop();
		System.out.println("Escopo desempilhado após o bloco de comandos: Nível " + level);
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

	/* Operações */
	public TypeSymbol visit(BinOP binaryExpr) {
		TypeSymbol leftType = visit(binaryExpr.getLeft());
		TypeSymbol rightType = visit(binaryExpr.getRight());

		// Verificar operadores aritmético (+, -, *, /)
		if (binaryExpr instanceof Plus | binaryExpr instanceof Minus | binaryExpr instanceof Mul
				| binaryExpr instanceof Div) {
			if (leftType.equals(new TypeSymbol("Int")) && rightType.equals(new TypeSymbol("Int"))) {
				return new TypeSymbol("Int");
			} else if (leftType.equals(new TypeSymbol("Float")) && rightType.equals(new TypeSymbol("Float"))) {
				return new TypeSymbol("Float");
			} else {
				throw new TypeMismatchException("Erro semântico: tipos incompatíveis na operação de " + binaryExpr.toString());
			}
		}

		// Verificar operadores aritmético (%)
		if (binaryExpr instanceof Mod) {
			if (leftType.equals(new TypeSymbol("Int")) && rightType.equals(new TypeSymbol("Int"))) {
				return new TypeSymbol("Int");
			} else {
				throw new TypeMismatchException("Erro semântico: '%' só pode ser usado com inteiros.");
			}
		}

		// Verificar operadores relacionais (==, !=, <)
		if (binaryExpr instanceof EQ || binaryExpr instanceof NotEq || binaryExpr instanceof LessThan) {
			if ((leftType.equals(new TypeSymbol("Int")) || leftType.equals(new TypeSymbol("Float"))
					|| leftType.equals(new TypeSymbol("Char"))) && leftType.equals(rightType)) {
				return new TypeSymbol("Bool");
			} else {
				throw new TypeMismatchException("Erro semântico: tipos incompatíveis na operação relacional.");
			}
		}

		// Verificar operador lógico (&&)
		if (binaryExpr instanceof And) {
			if (leftType.equals(new TypeSymbol("Bool")) && rightType.equals(new TypeSymbol("Bool"))) {
				return new TypeSymbol("Bool");
			} else {
				throw new TypeMismatchException("Erro semântico: '&&' só pode ser usado com booleanos.");
			}
		}

		throw new TypeMismatchException("Erro semântico: operador binário desconhecido.");
	}

	public TypeSymbol visit(Neg neg) {
		TypeSymbol exprType = visit(neg.getExpr());

		String operator = neg.toString();

		// Verifique o tipo para o operador '!'
		if (operator.equals("!")) {
			if (exprType.equals(new TypeSymbol("Bool"))) {
				return new TypeSymbol("Bool");
			} else {
				throw new TypeMismatchException("Erro semântico: '!' só pode ser usado com booleanos.");
			}
		}

		throw new TypeMismatchException("Erro semântico: operador unário desconhecido '" + operator + "'");
	}

	public TypeSymbol visit(Not not) {
		// Obtenha o tipo da subexpressão e1
		TypeSymbol exprType = visit(not.getExpr());

		// Obtenha o operador unário
		String operator = not.toString();

		// Verifique o tipo para o operador '!'
		if (operator.equals("-"))

		{
			if (exprType.equals(new TypeSymbol("Int")) || exprType.equals(new TypeSymbol("Float"))) {
				return exprType; // Retorna o mesmo tipo da expressão
			} else {
				throw new TypeMismatchException("Erro semântico: '-' só pode ser usado com inteiros ou floats.");
			}
		}

		throw new TypeMismatchException("Erro semântico: operador unário desconhecido '" + operator + "'");

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

		Node cmd1 = stmtList.getCmd1();
		if (cmd1 != null) {
			System.out.println(cmd1);
			cmd1.accept(this); // Visitando cada comando na lista
		} else {
			System.out.println("StmtList is empty.");
		}

		Node cmd2 = stmtList.getCmd2();
		if (cmd2 != null) {
			System.out.println("Visiting cmd2: " + cmd2);
			cmd2.accept(this); // Visitando a main
		} else {
			System.out.println("cmd2 is empty.");
		}

	}

	public void visit(Node node) {
		if (node instanceof BlockCmd) {
			visit((BlockCmd) node);
		} else if (node instanceof Cmd) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			visit((Cmd) node);
		} else if (node instanceof If) {
			visit((If) node);
		} else {
			throw new RuntimeException("Erro semântico: Tipo de Node desconhecido: " + node.getClass().getSimpleName());
		}
	}

	public void visit(Visitable p) {

	}
}
