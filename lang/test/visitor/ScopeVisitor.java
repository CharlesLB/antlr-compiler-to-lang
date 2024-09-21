package lang.test.visitor;

import lang.core.ast.definitions.StmtList;
import lang.core.ast.definitions.Type;
import lang.core.ast.Node;
import lang.core.ast.definitions.Cmd;
import lang.core.ast.definitions.Data;
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
import lang.core.ast.lvalue.FunLValue;
import lang.core.ast.lvalue.IDLValue;
import lang.core.ast.lvalue.LValue;
import lang.core.ast.statements.commands.Assign;
import lang.core.ast.statements.commands.BlockCmd;
import lang.core.ast.statements.commands.If;
import lang.core.ast.statements.commands.Iterate;
import lang.core.ast.statements.commands.Print;
import lang.core.ast.statements.commands.ReadLValue;
import lang.core.ast.statements.commands.Return;
import lang.core.ast.statements.data.Decl;
import lang.core.ast.types.Btype;
import lang.core.ast.types.IDType;
import lang.test.visitor.scope.Pair;
import lang.test.visitor.scope.ScopeTable;
import lang.test.visitor.symbols.DataSymbol;
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
				returnTypeSymbol.add(typeSymbol);
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
				System.out.println("Cmd: " + cmd + " " + cmd.getClass());
				cmd.accept(this);
			}
		} else {
			System.out.println("Function body is empty.");
			System.out.println("Escopo desempilhado: Nível " + level);
		}

		level = scopes.pop();
		currentFunction = null;
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

	public void visit(Print printCmd) {
		// Passo 1: Obter a expressão a ser impressa
		Expr expr = printCmd.getExpr();

		// Passo 2: Verificar o tipo da expressão
		TypeSymbol exprType = visit(expr);

		// Passo 3: Verificar se o tipo da expressão está entre os permitidos: {Int,
		// Float, Char, Bool}
		if (!exprType.equals(new TypeSymbol("Int")) &&
				!exprType.equals(new TypeSymbol("Float")) &&
				!exprType.equals(new TypeSymbol("Char")) &&
				!exprType.equals(new TypeSymbol("Bool"))) {
			throw new RuntimeException(
					"Erro semântico: O comando 'print' só pode imprimir expressões dos tipos Int, Float, Char ou Bool.");
		}

		// Passo 4: O ambiente e o contexto não são alterados, então apenas imprimimos
		// uma mensagem de sucesso
		System.out.println("Comando 'print' verificado com sucesso para a expressão: " + expr);
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
			} else {
				VarSymbol var = (VarSymbol) symbol.first();
				return var.getType();
			}
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
		VarSymbol var = (VarSymbol) symbol.first();

		return var.getType();
	}

	public TypeSymbol visit(Expr exp) {
		TypeSymbol exprType;

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
			exprType = visit((IDLValue) exp);
		} else if (exp instanceof FunCallWithIndex) {
			exprType = visit((FunCallWithIndex) exp);
		} else {
			throw new TypeMismatchException("Erro semântico: tipo de expressão desconhecido.");
		}

		System.out.println("Tipo da expressão é: " + exprType);
		return exprType;
	}

	public void visit(Assign assignment) {
		System.out.println("Atribuição: " + assignment.getID().getName() + " = " + assignment.getExp());

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

	public void visit(FunLValue funLValue) {
		System.out.println("Chamando Função: " + funLValue.getFunctionName().getName());

		// Obter o nome da função
		String functionName = funLValue.getFunctionName().getName();
		Pair<Symbol, Integer> symbol = scopes.search(functionName);
		System.out.println(symbol);

		// Verificar se a função foi declarada
		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: função '" + functionName + "' não foi declarada.");
		}
		FunctionSymbol functionSymbol = (FunctionSymbol) symbol.first();

		// Verificar os tipos dos argumentos
		List<Expr> arguments = funLValue.getArguments();
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

		// Verificar os lvalues e o número de retornos da função
		List<LValue> lvalues = funLValue.getLValues(); // Obter as variáveis à esquerda (lvalues)
		List<TypeSymbol> returnTypes = functionSymbol.getReturnTypes(); // Obter os tipos de retorno da função

		if (lvalues.size() != returnTypes.size()) {
			throw new TypeMismatchException(
					"Erro semântico: O número de variáveis à esquerda (lvalues) não corresponde ao número de retornos da função '"
							+ functionName + "'.");
		}

		// Verificar e inferir tipos das variáveis lvalues
		for (int i = 0; i < lvalues.size(); i++) {
			System.out.println("Visitando: " + lvalues.get(i));

			// Verificar se a variável está no escopo
			LValue lvalue = lvalues.get(i);
			Pair<Symbol, Integer> lvalueSymbol = scopes.search(lvalue.toString());

			TypeSymbol returnType = returnTypes.get(i); // Obter o tipo de retorno da função

			if (lvalueSymbol == null) {
				// A variável não foi declarada, inferir o tipo com base no retorno da função
				System.out.println("Inferindo tipo da variável '" + lvalue + "' como: " + returnType);

				// Adicionar ao escopo
				VarSymbol inferredVar = new VarSymbol(lvalue.toString(), returnType, null); // O valor pode ser inicializado
																																										// como null
				scopes.put(lvalue.toString(), inferredVar);
				System.out.println("Variável '" + lvalue + "' adicionada ao escopo com o tipo inferido: " + returnType);
			} else {
				// A variável foi encontrada, verificar o tipo
				VarSymbol lvalueVarSymbol = (VarSymbol) lvalueSymbol.first();
				TypeSymbol lvalueType = lvalueVarSymbol.getType();

				System.out.println(lvalueType + " " + returnType);
				if (!lvalueType.equals(returnType)) {
					throw new TypeMismatchException("Erro semântico: Tipo incompatível para a variável " + (i + 1)
							+ ". Esperado: " + returnType + ", Encontrado: " + lvalueType);
				}
			}
		}

		System.out.println("Função '" + functionName + "' verificada com sucesso com múltiplas variáveis de retorno.");
	}

	public void visit(Data data) {
		String dataTypeName = data.getID().getName();

		// Passo 1: Verificar se o tipo já foi declarado no escopo global (nível 0)
		if (scopes.search(dataTypeName) != null) {
			throw new TypeMismatchException("Erro semântico: O tipo '" + dataTypeName + "' já foi declarado.");
		}

		// Passo 2: Criar um escopo local para os campos da estrutura 'Data'
		level = scopes.push(); // Empilhar um novo escopo para os campos de 'Data'

		// Passo 3: Inicializar a lista de campos (VarSymbol) para o Data
		List<VarSymbol> fields = new ArrayList<>();

		// Passo 4: Visitar cada declaração de campo (Decl) e adicionar à lista de
		// campos
		for (Decl decl : data.getDeclarations()) {
			visit(decl); // Visitar cada Decl e verificar a declaração
			TypeSymbol type = new TypeSymbol(decl.getType().toString());
			VarSymbol varSymbol = new VarSymbol(decl.getID().getName(), type, null);
			fields.add(varSymbol); // Adicionar à lista de campos (não no escopo global)
		}

		// Passo 5: Criar um símbolo para o Data e adicionar à tabela de símbolos global
		// (nível 0)
		DataSymbol dataSymbol = new DataSymbol(dataTypeName, fields);
		this.level = 0; // Definir o escopo para o nível global
		scopes.put(dataTypeName, dataSymbol); // Adicionar ao SymbolTable global

		// Desempilhar o escopo após adicionar os campos no escopo local
		level = scopes.pop();

		System.out.println("Tipo 'Data' definido com sucesso: " + dataSymbol);
	}

	public void visit(Decl decl) {
		String fieldName = decl.getID().getName(); // Nome do campo
		Type fieldType = decl.getType(); // Tipo do campo

		// Verificar se o campo já foi declarado anteriormente
		if (scopes.search(fieldName) != null) {
			throw new TypeMismatchException("Erro semântico: O campo '" + fieldName + "' já foi declarado.");
		}

		// Adicionar o campo à tabela de símbolos local
		TypeSymbol type = new TypeSymbol(decl.getType().toString());
		VarSymbol varSymbol = new VarSymbol(fieldName, type, null);
		scopes.put(fieldName, varSymbol);

		System.out.println("Campo '" + fieldName + "' declarado com sucesso: " + fieldType);
	}

	public void visit(Return returnCmd) {
		System.out.println("Entrando no 'visit(Return)': " + returnCmd);

		if (currentFunction == null) {
			throw new TypeMismatchException("Erro semântico: comando 'return' fora de uma função.");
		}

		List<Type> expectedReturnTypes = currentFunction.getReturnTypes();
		List<Expr> returnExpressions = returnCmd.getExprList();

		if (expectedReturnTypes.size() != returnExpressions.size()) {
			throw new TypeMismatchException("Erro semântico: Número incorreto de valores retornados.");
		}

		for (int i = 0; i < returnExpressions.size(); i++) {
			Type expectedType = expectedReturnTypes.get(i);
			TypeSymbol returnTypeSymbol = visit(returnExpressions.get(i));

			if (!returnTypeSymbol.equals(visit(expectedType))) {
				throw new TypeMismatchException("Erro semântico: Tipo incompatível no valor de retorno " + (i + 1) +
						". Esperado: " + expectedType + ", Encontrado: " + returnTypeSymbol);
			}
		}

		System.out.println("Comando 'return' verificado com sucesso.");
	}

	public void visit(If ifStmt) {
		System.out.println("Visitando estrutura If no escopo " + level);

		// 1. Verificar a expressão condicional (exp) deve ser do tipo Bool
		TypeSymbol conditionType = visit(ifStmt.getExp());
		if (!conditionType.equals(new TypeSymbol("Bool"))) {
			throw new TypeMismatchException("Erro semântico: A condição do 'if' deve ser do tipo Bool.");
		}
		System.out.println("Condição do 'if' verificada com sucesso. Tipo: Bool");

		// 2. Empilhar um novo escopo para o bloco 'then' (thn)
		level = scopes.push();
		System.out.println("Novo escopo empilhado para o bloco 'then': Nível " + level);

		// 3. Visitar o bloco 'then'
		if (ifStmt.getThenCmd() instanceof BlockCmd) {
			visit((BlockCmd) ifStmt.getThenCmd()); // Visitar o bloco de comandos
		} else {
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
			throw new TypeMismatchException("Erro semântico: Bloco de comandos vazio.");
		}

		for (Cmd cmd : commands) {
			visit(cmd);
		}

		level = scopes.pop();
		System.out.println("Escopo desempilhado após o bloco de comandos: Nível " + level);
	}

	public void visit(ReadLValue readCmd) {
		String varName = readCmd.getLValue().toString();

		// Procurar a variável no escopo atual (Γ) usando a tabela de símbolos (Θ, Γ)
		Pair<Symbol, Integer> symbol = scopes.search(varName);

		// Verificar se a variável foi declarada
		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: Variável '" + varName + "' não foi declarada.");
		}

		// Obter o tipo da variável
		VarSymbol varSymbol = (VarSymbol) symbol.first();
		TypeSymbol varType = varSymbol.getType();

		// Verificar se o tipo da variável está entre os tipos permitidos: {Int, Float,
		// Char, Bool}
		if (!varType.equals(new TypeSymbol("Int")) &&
				!varType.equals(new TypeSymbol("Float")) &&
				!varType.equals(new TypeSymbol("Char")) &&
				!varType.equals(new TypeSymbol("Bool"))) {
			throw new TypeMismatchException(
					"Erro semântico: Variável '" + varName + "' deve ser de tipo Int, Float, Char ou Bool.");
		}

		// Se tudo estiver correto, a leitura está verificada com sucesso
		System.out.println("Comando 'read' verificado com sucesso para a variável: " + varName);
	}

	public void visit(Iterate iterateCmd) {
		System.out.println("Visitando comando 'iterate' no escopo " + level);

		// Passo 1: Verificar a expressão de controle do laço (deve ser do tipo Int)
		TypeSymbol conditionType = visit(iterateCmd.getExpr());
		System.out.println("AAAAAAA: " + conditionType);
		if (!conditionType.equals(new TypeSymbol("Int"))) {
			throw new RuntimeException("Erro semântico: A expressão do comando 'iterate' deve ser do tipo Int.");
		}
		System.out.println("Expressão de controle do 'iterate' verificada com sucesso. Tipo: Int");

		// Passo 2: Empilhar um novo escopo para o bloco de comandos (cs)
		level = scopes.push();
		System.out.println("Novo escopo empilhado para o bloco 'iterate': Nível " + level);

		// Passo 3: Visitar o bloco de comandos (cs) dentro do laço -> do tipo BlockCmd
		visit(iterateCmd.getBody());

		// Passo 4: Desempilhar o escopo após visitar o bloco de comandos
		level = scopes.pop();
		System.out.println("Escopo desempilhado após o bloco 'iterate': Nível " + level);

		// Passo 5: O ambiente e o contexto (V, Γ) são mantidos
	}

	/* Operações */
	public TypeSymbol visit(BinOP binOP) {
		TypeSymbol leftType = visit(binOP.getLeft());
		TypeSymbol rightType = visit(binOP.getRight());

		// Verificar operadores aritmético (+, -, *, /)
		if (binOP instanceof Plus | binOP instanceof Minus | binOP instanceof Mul
				| binOP instanceof Div) {
			if (leftType.equals(new TypeSymbol("Int")) && rightType.equals(new TypeSymbol("Int"))) {
				return new TypeSymbol("Int");
			} else if (leftType.equals(new TypeSymbol("Float")) && rightType.equals(new TypeSymbol("Float"))) {
				return new TypeSymbol("Float");
			} else {
				throw new TypeMismatchException("Erro semântico: tipos incompatíveis na operação de " + binOP.toString());
			}
		}

		// Verificar operadores aritmético (%)
		if (binOP instanceof Mod) {
			if (leftType.equals(new TypeSymbol("Int")) && rightType.equals(new TypeSymbol("Int"))) {
				return new TypeSymbol("Int");
			} else {
				throw new TypeMismatchException("Erro semântico: '%' só pode ser usado com inteiros.");
			}
		}

		// Verificar operadores relacionais (==, !=, <)
		if (binOP instanceof EQ || binOP instanceof NotEq || binOP instanceof LessThan) {
			if ((leftType.equals(new TypeSymbol("Int")) || leftType.equals(new TypeSymbol("Float"))
					|| leftType.equals(new TypeSymbol("Char"))) && leftType.equals(rightType)) {
				return new TypeSymbol("Bool");
			} else {
				throw new TypeMismatchException("Erro semântico: tipos incompatíveis na operação relacional.");
			}
		}

		// Verificar operador lógico (&&)
		if (binOP instanceof And) {
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
		TypeSymbol exprType = visit(not.getExpr());

		String operator = not.toString();

		// Verifique o tipo para o operador '!'
		if (operator.equals("-"))

		{
			if (exprType.equals(new TypeSymbol("Int")) || exprType.equals(new TypeSymbol("Float"))) {
				return exprType;
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

	public void visit(Cmd cmd) {
		if (cmd instanceof BlockCmd) {
			visit((BlockCmd) cmd);
		} else if (cmd instanceof If) {
			visit((If) cmd);
		} else if (cmd instanceof Return) {
			visit((Return) cmd);
		} else if (cmd instanceof Print) {
			visit((Print) cmd);
		} else if (cmd instanceof Assign) {
			visit((Assign) cmd);
		} else if (cmd instanceof FunLValue) {
			visit((FunLValue) cmd);
		} else if (cmd instanceof Iterate) {
			visit((Iterate) cmd);
		} else {
			throw new TypeMismatchException(
					"Erro semântico: Tipo de comando desconhecido: " + cmd.getClass().getSimpleName());
		}
	}

	public void visit(Node node) {
		if (node instanceof BlockCmd) {
			visit((BlockCmd) node);
		} else if (node instanceof If) {
			visit((If) node);
		} else if (node instanceof Return) {
			visit((Return) node);
		} else if (node instanceof Print) {
			visit((Print) node);
		} else if (node instanceof Assign) {
			visit((Assign) node);
		} else {
			throw new TypeMismatchException(
					"Erro semântico: Tipo de comando desconhecido: " + node.getClass().getSimpleName());
		}
	}

	public void visit(Visitable p) {

	}
}
