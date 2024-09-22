/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
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
import lang.core.ast.expressions.NewArray;
import lang.core.ast.expressions.NewObject;
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
import lang.core.ast.lvalue.AssignLValue;
import lang.core.ast.lvalue.AttrAccessLValue;
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
import lang.core.ast.types.MatrixType;
import lang.test.visitor.context.Pair;
import lang.test.visitor.context.ContextTable;
import lang.test.visitor.symbols.DataSymbol;
import lang.test.visitor.symbols.FunctionSymbol;
import lang.test.visitor.symbols.ObjectSymbol;
import lang.test.visitor.symbols.Symbol;
import lang.test.visitor.symbols.TypeSymbol;
import lang.test.visitor.symbols.VarSymbol;
import lang.utils.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class ContextVisitor extends Visitor {

	private ContextTable contexts;
	private int level;
	private Fun currentFunction;

	private boolean hasMainFunction = false;

	public ContextVisitor() {
		contexts = new ContextTable();
		level = contexts.getLevel();
		currentFunction = null;
	}

	public void visit(Fun p) {
		currentFunction = p;

		level = contexts.push();
		// System.out.println("<<<<<<<<<< Função: " + functionName + " / " + level + "
		// >>>>>>>>");

		List<Param> params = p.getParams();
		if (params != null && !params.isEmpty()) {
			for (Param param : params) {
				visit(param);
			}
		}

		List<Type> returnTypes = p.getReturnTypes();
		if (returnTypes != null && !returnTypes.isEmpty()) {
			for (Type returnType : returnTypes) {
				returnType.accept(this);
			}
		}

		List<Cmd> body = p.getBody();
		if (body != null && !body.isEmpty()) {
			for (Cmd cmd : body) {
				cmd.accept(this);
			}
		}

		level = contexts.pop();
		currentFunction = null;
	}

	public void visit(Param param) {
		String paramName = param.getID().getName();
		TypeSymbol paramType = visit(param.getType());

		if (contexts.search(paramName) != null) {
			throw new TypeMismatchException("Erro semântico: parâmetro '" + paramName +
					"' já foi declarado.");
		}

		VarSymbol paramSymbol = new VarSymbol(paramName, paramType, null);
		contexts.put(paramName, paramSymbol);
	}

	public TypeSymbol visit(Type type) {
		if (type instanceof Btype) {

			String typeName = ((Btype) type).getType();
			return new TypeSymbol(typeName);

		} else if (type instanceof IDType) {

			String typeName = ((IDType) type).getName();
			return new TypeSymbol(typeName);

		} else if (type instanceof MatrixType) {
			MatrixType matrixType = (MatrixType) type;

			try {
				TypeSymbol baseType = visit(matrixType.getBaseType());

				int dimensions = matrixType.getDimensions();
				if (dimensions <= 0) {
					throw new TypeMismatchException("Erro semântico: número inválido de dimensões para o array.");
				}

				// Criação do TypeSymbol para arrays (Ponto[])
				TypeSymbol arrayType = new TypeSymbol(baseType, dimensions);

				return arrayType;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		throw new TypeMismatchException("Erro semântico: tipo inválido.");
	}

	public void visit(Print printCmd) {
		Expr expr = printCmd.getExpr();

		TypeSymbol exprType = visit(expr);

		if (!exprType.equals(new TypeSymbol("Int")) &&
				!exprType.equals(new TypeSymbol("Float")) &&
				!exprType.equals(new TypeSymbol("Char")) &&
				!exprType.equals(new TypeSymbol("Bool"))) {
			throw new RuntimeException(
					"Erro semântico: O comando 'print' só pode imprimir expressões dos tipos Int, Float, Char ou Bool.");
		}
	}

	public TypeSymbol visit(LValue lvalue, Expr expr) {
		// Caso 1: lvalue é um ID simples
		if (lvalue instanceof IDLValue) {
			String variableName = ((IDLValue) lvalue).getName();

			Pair<Symbol, Integer> symbol = contexts.search(variableName);

			// Se a variável não foi encontrada no escopo, infere o tipo e a insere
			if (symbol == null) {
				TypeSymbol inferredType = visit(expr);

				VarSymbol newSymbol = new VarSymbol(variableName, inferredType, expr);
				contexts.put(variableName, newSymbol);

				return inferredType;
			} else {
				VarSymbol var = (VarSymbol) symbol.first();
				return var.getType();
			}
			// Caso 2: lvalue é um acesso a array (lvalue[exp])
		} else if (lvalue instanceof ArrayAccessLValue) {
			Expr array = ((ArrayAccessLValue) lvalue).getArray();
			Expr index = ((ArrayAccessLValue) lvalue).getIndex();

			TypeSymbol arrayType = visit(array);
			if (!arrayType.isArray()) {
				throw new TypeMismatchException("Erro semântico: o tipo " + arrayType + " não é um array.");
			}

			TypeSymbol indexType = visit(index);
			if (!indexType.equals(new TypeSymbol("Int"))) {
				throw new TypeMismatchException("Erro semântico: índice do array deve ser do tipo Int.");
			}

			return arrayType.getElementType();

			// Caso 3: lvalue é um acesso a campo (lvalue.ID)
		} else if (lvalue instanceof AttrAccessLValue) {

			return visit((AttrAccessLValue) lvalue);

		} else {
			throw new TypeMismatchException("Erro semântico: tipo de lvalue desconhecido.");
		}
	}

	public TypeSymbol visit(IDLValue idlValue) {
		String variableName = ((IDLValue) idlValue).getName();

		Pair<Symbol, Integer> symbol = contexts.search(variableName);

		if (symbol == null) {
			throw new TypeMismatchException("Variável '" + variableName + "' não foi declarada no escopo atual.");
		}
		VarSymbol var = (VarSymbol) symbol.first();

		return var.getType();
	}

	public TypeSymbol visit(Expr exp) {
		TypeSymbol exprType;

		try {
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
			} else if (exp instanceof BoolLiteral) {
				exprType = new TypeSymbol("Bool");
			} else if (exp instanceof IDLValue) {
				exprType = visit((IDLValue) exp);
			} else if (exp instanceof FunCallWithIndex) {
				exprType = visit((FunCallWithIndex) exp);
			} else if (exp instanceof NewObject) {
				exprType = visit((NewObject) exp);
			} else if (exp instanceof NewArray) {
				exprType = visit((NewArray) exp);
			} else if (exp instanceof ArrayAccessLValue) {
				exprType = visit((ArrayAccessLValue) exp);
			} else if (exp instanceof AttrAccessLValue) {
				exprType = visit((AttrAccessLValue) exp);
			} else {
				throw new TypeMismatchException("Erro semântico: tipo de expressão desconhecida.");
			}
		} catch (Exception e) {
			throw e;
		}
		return exprType;
	}

	public void visit(Assign assignment) {
		IDLValue variable = assignment.getID();
		Expr expr = assignment.getExp();

		// Visita o lado esquerdo (lv)
		TypeSymbol varType = visit(variable, expr);

		// Visita o lado direito (e)
		TypeSymbol exprType = visit(expr);

		// Verifica se os tipos são compatíveis
		if (!varType.equals(exprType)) {
			throw new TypeMismatchException("Tipo incompatível na atribuição: "
					+ varType + " != " + exprType);
		}

		// Se tudo correto, atualiza a tabela de símbolos
		// VarSymbol symbol = new VarSymbol(variable.getName(), varType, expr);
		VarSymbol symbol = new VarSymbol(variable.getName(), varType, null);
		contexts.put(variable.getName(), symbol);

		// contexts.printContexts();
	}

	public TypeSymbol visit(FunCallWithIndex funCallWithIndex) {
		StringBuilder signature = new StringBuilder(funCallWithIndex.getFunctionName().getName() + "(");

		for (Expr argument : funCallWithIndex.getArguments()) {
			TypeSymbol argType = visit(argument);
			signature.append(argType.getName()).append(", ");
		}

		if (!funCallWithIndex.getArguments().isEmpty()) {
			signature.setLength(signature.length() - 2);
		}
		signature.append(")");

		Pair<Symbol, Integer> symbol = contexts.search(signature.toString());

		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: função '" + signature + "' não foi declarada.");
		}
		FunctionSymbol functionSymbol = (FunctionSymbol) symbol.first();

		List<Expr> arguments = funCallWithIndex.getArguments();
		List<VarSymbol> expectedParameters = functionSymbol.getParameters();

		if (arguments.size() != expectedParameters.size()) {
			throw new TypeMismatchException(
					"Erro semântico: número de argumentos incorreto para a função '" + functionSymbol.getName() + "'.");
		}

		for (int i = 0; i < arguments.size(); i++) {
			TypeSymbol argType = visit(arguments.get(i));
			TypeSymbol expectedType = expectedParameters.get(i).getType();

			// Se ambos são arrays, verificar se as dimensões e os tipos base são iguais
			if (argType.isArray() && expectedType.isArray()) {
				// Comparar o tipo base dos arrays
				if (!argType.getElementType().equals(expectedType.getElementType())) {
					throw new TypeMismatchException("Erro semântico: tipo base do array do argumento " + (i + 1)
							+ " da função '" + functionSymbol.getName() + "' não corresponde. Esperado: "
							+ expectedType.getElementType()
							+ ", Encontrado: " + argType.getElementType());
				}
			} else if (!argType.equals(expectedType)) {
				// Se não são arrays, comparar os tipos diretamente
				throw new TypeMismatchException("Erro semântico: tipo do argumento " + (i + 1) + " da função '"
						+ functionSymbol.getName() + "' não corresponde. Esperado: " + expectedType + ", Encontrado: " + argType);
			}
		}

		List<TypeSymbol> returnTypes = functionSymbol.getReturnTypes();

		TypeSymbol indexType = visit(funCallWithIndex.getIndexExpr());
		if (!indexType.equals(new TypeSymbol("Int"))) {
			throw new TypeMismatchException("Erro semântico: o índice deve ser do tipo Int.");
		}

		int index = (int) funCallWithIndex.getIndexExpr().interpret(null);
		if (index >= returnTypes.size() || index < 0) {
			throw new TypeMismatchException("Erro semântico: índice de retorno fora dos limites.");
		}

		return returnTypes.get(index);
	}

	public void visit(FunLValue funLValue) {
		StringBuilder signature = new StringBuilder(funLValue.getFunctionName().getName() + "(");

		for (Expr argument : funLValue.getArguments()) {
			TypeSymbol argType = visit(argument);
			signature.append(argType.getName()).append(", ");
		}

		if (!funLValue.getArguments().isEmpty()) {
			signature.setLength(signature.length() - 2);
		}
		signature.append(")");

		Pair<Symbol, Integer> symbol = contexts.search(signature.toString());

		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: função '" + signature + "' não foi declarada.");
		}

		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: função '" + signature + "' não foi declarada.");
		}
		FunctionSymbol functionSymbol = (FunctionSymbol) symbol.first();

		List<Expr> arguments = funLValue.getArguments();
		List<VarSymbol> expectedParameters = functionSymbol.getParameters();

		if (arguments.size() != expectedParameters.size()) {
			throw new TypeMismatchException(
					"Erro semântico: número de argumentos incorreto para a função '" + functionSymbol.getName() + "'.");
		}

		for (int i = 0; i < arguments.size(); i++) {
			TypeSymbol argType = visit(arguments.get(i));
			TypeSymbol expectedType = expectedParameters.get(i).getType();

			if (argType.isArray() && expectedType.isArray()) {
				if (!argType.getElementType().equals(expectedType.getElementType())) {
					throw new TypeMismatchException("Erro semântico: tipo base do array do argumento " + (i + 1)
							+ " da função '" + functionSymbol.getName() + "' não corresponde. Esperado: "
							+ expectedType.getElementType()
							+ ", Encontrado: " + argType.getElementType());
				}
			} else if (!argType.equals(expectedType)) {
				throw new TypeMismatchException("Erro semântico: tipo do argumento " + (i + 1) + " da função '"
						+ functionSymbol.getName() + "' não corresponde. Esperado: " + expectedType + ", Encontrado: " + argType);
			}
		}

		List<LValue> lvalues = funLValue.getLValues();
		List<TypeSymbol> returnTypes = functionSymbol.getReturnTypes();

		if (lvalues.size() != returnTypes.size()) {
			throw new TypeMismatchException(
					"Erro semântico: O número de variáveis à esquerda (lvalues) não corresponde ao número de retornos da função '"
							+ functionSymbol.getName() + "'.");
		}

		for (int i = 0; i < lvalues.size(); i++) {
			LValue lvalue = lvalues.get(i);
			Pair<Symbol, Integer> lvalueSymbol = contexts.search(lvalue.toString());

			TypeSymbol returnType = returnTypes.get(i);

			if (lvalueSymbol == null) {

				VarSymbol inferredVar = new VarSymbol(lvalue.toString(), returnType, null);
				contexts.put(lvalue.toString(), inferredVar);
			} else {
				VarSymbol lvalueVarSymbol = (VarSymbol) lvalueSymbol.first();
				TypeSymbol lvalueType = lvalueVarSymbol.getType();

				if (!lvalueType.equals(returnType)) {
					throw new TypeMismatchException("Erro semântico: Tipo incompatível para a variável " + (i + 1)
							+ ". Esperado: " + returnType + ", Encontrado: " + lvalueType);
				}
			}
		}
	}

	public void visit(Data data) {
		String dataTypeName = data.getID().getName();

		List<VarSymbol> fields = new ArrayList<>();
		for (Decl decl : data.getDeclarations()) {
			String fieldName = decl.getID().getName();
			if (fields.stream().anyMatch(f -> f.getName().equals(fieldName))) {
				throw new TypeMismatchException(
						"Erro semântico: O campo '" + fieldName + "' já foi declarado na estrutura '"
								+ dataTypeName + "'.");
			}
			visit(decl);
		}
	}

	public void visit(Decl decl) {
	}

	public void visit(Return returnCmd) {
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
				throw new TypeMismatchException("Erro semântico: Tipo incompatível no valor de retorno " + (i + 1)
						+ ". Esperado: " + expectedType + ", Encontrado: " + returnTypeSymbol);
			}
		}

		// System.out.println("Comando 'return' verificado com sucesso.");
	}

	public void visit(If ifStmt) {
		// 1. Verificar a expressão condicional (exp) deve ser do tipo Bool
		TypeSymbol conditionType = visit(ifStmt.getExp());
		if (!conditionType.equals(new TypeSymbol("Bool"))) {
			throw new TypeMismatchException("Erro semântico: A condição do 'if' deve ser do tipo Bool.");
		}

		// 2. Empilhar um novo escopo para o bloco 'then' (thn)
		level = contexts.push();

		// 3. Visitar o bloco 'then'
		if (ifStmt.getThenCmd() instanceof BlockCmd) {
			visit((BlockCmd) ifStmt.getThenCmd());
		} else {
			visit(ifStmt.getThenCmd());
		}

		// 4. Desempilhar o escopo do bloco 'then' após a visita
		level = contexts.pop();

		// 5. Se houver um bloco 'else', visitá-lo
		if (ifStmt.getThenEls() != null) {
			level = contexts.push();

			if (ifStmt.getThenEls() instanceof BlockCmd) {
				visit((BlockCmd) ifStmt.getThenEls());
			} else {
				visit(ifStmt.getThenEls());
			}

			level = contexts.pop();
		}
	}

	public void visit(BlockCmd blockCmd) {
		level = contexts.push();

		List<Cmd> commands = blockCmd.getCommands();

		if (commands.isEmpty()) {
			throw new TypeMismatchException("Erro semântico: Bloco de comandos vazio.");
		}

		for (Cmd cmd : commands) {
			visit(cmd);
		}

		level = contexts.pop();
	}

	public void visit(ReadLValue readCmd) {
		String varName = readCmd.getLValue().toString();

		Pair<Symbol, Integer> symbol = contexts.search(varName);
		if (symbol == null) {
			throw new TypeMismatchException("Erro semântico: Variável '" + varName + "' não foi declarada.");
		}

		VarSymbol varSymbol = (VarSymbol) symbol.first();
		TypeSymbol varType = varSymbol.getType();

		if (!varType.equals(new TypeSymbol("Int")) &&
				!varType.equals(new TypeSymbol("Float")) &&
				!varType.equals(new TypeSymbol("Char")) &&
				!varType.equals(new TypeSymbol("Bool"))) {
			throw new TypeMismatchException(
					"Erro semântico: Variável '" + varName + "' deve ser de tipo Int, Float, Char ou Bool.");
		}
	}

	public void visit(Iterate iterateCmd) {
		TypeSymbol conditionType = visit(iterateCmd.getExpr());
		if (!conditionType.equals(new TypeSymbol("Int"))) {
			throw new RuntimeException("Erro semântico: A expressão do comando 'iterate' deve ser do tipo Int.");
		}

		level = contexts.push();
		visit(iterateCmd.getBody());

		level = contexts.pop();
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

	public TypeSymbol visit(Not not) {
		TypeSymbol exprType = visit(not.getExpr());

		String operator = not.toString();

		if (operator.equals("!")) {
			if (exprType.equals(new TypeSymbol("Bool"))) {
				return new TypeSymbol("Bool");
			} else {
				throw new TypeMismatchException("Erro semântico: '!' só pode ser usado com booleanos.");
			}
		}

		throw new TypeMismatchException("Erro semântico: operador unário desconhecido'" + operator + "'");
	}

	public TypeSymbol visit(Neg neg) {
		TypeSymbol exprType = visit(neg.getExpr());

		String operator = neg.toString();

		if (operator.equals("-"))

		{
			if (exprType.equals(new TypeSymbol("Int")) || exprType.equals(new TypeSymbol("Float"))) {
				return exprType;
			} else {
				throw new TypeMismatchException("Erro semântico: '-' só pode ser usado com inteiros ou floats.");
			}
		}

		throw new TypeMismatchException("Erro semântico: operador unário desconhecido'" + operator + "'");
	}

	public TypeSymbol visit(NewObject newObject) {
		String typeName = newObject.getType().toString();

		Pair<Symbol, Integer> dataDefinition = contexts.search(typeName);

		if (dataDefinition == null) {
			throw new TypeMismatchException("Erro semântico: o tipo '" + typeName + "'não foi definido.");
		}

		return new TypeSymbol(typeName);
	}

	public TypeSymbol visit(NewArray newArray) {
		TypeSymbol sizeType = visit(newArray.getSize());
		if (!sizeType.equals(new TypeSymbol("Int"))) {
			throw new TypeMismatchException("Erro semântico: O tamanho do array deve ser do tipo Int.");
		}

		TypeSymbol baseType = visit(newArray.getType());
		if (baseType == null) {
			throw new TypeMismatchException("Erro semântico: tipo base inválido para o array.");
		}

		TypeSymbol arrayType = new TypeSymbol(baseType, 1);

		return arrayType;
	}

	public TypeSymbol visit(AttrAccessLValue attrAccessLValue) {
		AttrAccessLValue attrAccess = (AttrAccessLValue) attrAccessLValue;

		TypeSymbol objectType = visit(attrAccess.getObject());

		Pair<Symbol, Integer> symbol = contexts.search(objectType.getName());
		if (symbol == null || !(symbol.first() instanceof DataSymbol)) {
			throw new TypeMismatchException(
					"Erro semântico: o tipo '" + objectType.getName() + "' não foi definido como um tipo de dados.");
		}

		DataSymbol dataSymbol = (DataSymbol) symbol.first();
		String attrName = attrAccess.getAttr().getName();
		VarSymbol attribute = dataSymbol.getField(attrName);

		if (attribute == null) {
			throw new TypeMismatchException(
					"Erro semântico: o campo '" + attrName + "' não existe no tipo '" +
							dataSymbol.getName() + "'.");
		}

		return attribute.getType();
	}

	public TypeSymbol visit(ArrayAccessLValue arrayAccess) {
		TypeSymbol arrayType = visit(arrayAccess.getArray());

		if (!arrayType.isArray()) {
			throw new TypeMismatchException("Erro semântico: O objeto acessado não é um array.");
		}

		TypeSymbol indexType = visit(arrayAccess.getIndex());
		if (!indexType.equals(new TypeSymbol("Int"))) {
			throw new TypeMismatchException("Erro semântico: O índice do array deve ser do tipo Int.");
		}

		return arrayType.getElementType();
	}

	public TypeSymbol visit(AssignLValue assignLValue) {
		Expr expr = assignLValue.getExp();
		TypeSymbol exprType = visit(expr);

		LValue lvalue = assignLValue.getID();
		TypeSymbol lvalueType = visit(lvalue, expr);

		if (!lvalueType.equals(exprType)) {
			throw new TypeMismatchException(
					"Erro semântico: Tipo incompatível na atribuição. Esperado: " + lvalueType +
							", Encontrado: " + exprType);
		}

		return exprType;
	}

	/* TIPOS */
	public TypeSymbol visit(IntLiteral intLiteral) {
		return new TypeSymbol("Int");
	}

	public TypeSymbol visit(FloatLiteral floatLiteral) {
		return new TypeSymbol("Float");
	}

	public TypeSymbol visit(CharLiteral charLiteral) {
		return new TypeSymbol("Char");
	}

	public TypeSymbol visit(BoolLiteral boolLiteral) {
		return new TypeSymbol("Bool");
	}

	public TypeSymbol visit(NullLiteral nullLiteral) {
		return new TypeSymbol("Null");
	}

	/* Bases */
	public void visit(Cmd cmd) {
		if (cmd instanceof BlockCmd) {
			visit((BlockCmd) cmd);
		} else if (cmd instanceof AssignLValue) {
			visit((AssignLValue) cmd);
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
					"Erro semântico: Tipo de comando desconhecido: " +
							cmd.getClass().getSimpleName());
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
					"Erro semântico: Tipo de comando desconhecido: " +
							node.getClass().getSimpleName());
		}
	}

	public void visit(Visitable p) {

	}

	public void visit(StmtList stmtList) {
		preProcessDefinitions(stmtList);

		if (!hasMainFunction) {
			throw new TypeMismatchException("Erro semântico: A função 'main' não foi definida no programa.");
		}

		for (Node cmd : stmtList.getCommands()) {
			cmd.accept(this);
		}
	}

	private void preProcessDefinitions(StmtList stmtList) {
		for (Node cmd : stmtList.getCommands()) {
			preProcessDefinition(cmd);
		}
	}

	private void preProcessDefinition(Node node) {
		if (node instanceof Fun) {
			preVisitFunction((Fun) node);
		} else if (node instanceof Data) {
			preVisitData((Data) node);
		}
	}

	public void preVisitFunction(Fun p) {
		String functionName = p.getName().getName();

		StringBuilder signature = new StringBuilder(functionName + "(");

		List<TypeSymbol> returnTypeSymbol = new ArrayList<>();

		for (Type returnType : p.getReturnTypes()) {
			TypeSymbol typeSymbol = visit(returnType);
			returnTypeSymbol.add(typeSymbol);
		}

		List<VarSymbol> parameterSymbols = new ArrayList<>();
		if (p.getParams() != null) {
			for (Param param : p.getParams()) {
				String paramName = param.getID().getName();
				TypeSymbol paramType = visit(param.getType());

				VarSymbol varSymbol = new VarSymbol(paramName, paramType, null);
				parameterSymbols.add(varSymbol);

				// Adicionar o tipo do parâmetro à assinatura
				signature.append(paramType.getName()).append(", ");
			}
		}

		// Remover a última vírgula e espaço da assinatura
		if (!parameterSymbols.isEmpty()) {
			signature.setLength(signature.length() - 2);
		}
		signature.append(")");

		// Registrar a função no escopo global usando a assinatura completa
		FunctionSymbol functionSymbol = new FunctionSymbol(signature.toString(),
				returnTypeSymbol, parameterSymbols);
		contexts.put(signature.toString(), functionSymbol);

		if (functionName.equals("main") && parameterSymbols.isEmpty()) {
			hasMainFunction = true;
		}
	}

	public void preVisitData(Data data) {
		String dataTypeName = data.getID().getName();
		if (contexts.search(dataTypeName) != null) {
			throw new TypeMismatchException("Erro semântico: O tipo '" + dataTypeName +
					"' já foi declarado.");
		}

		List<VarSymbol> fields = new ArrayList<>();
		for (Decl decl : data.getDeclarations()) {
			String fieldName = decl.getID().getName();
			TypeSymbol fieldType = visit(decl.getType());

			if (fields.stream().anyMatch(f -> f.getName().equals(fieldName))) {
				throw new TypeMismatchException("Erro semântico: O campo '" + fieldName + "'já foi declarado na estrutura.");
			}

			VarSymbol varSymbol = new VarSymbol(fieldName, fieldType, null);
			fields.add(varSymbol);
		}

		DataSymbol dataSymbol = new DataSymbol(dataTypeName, fields);
		contexts.put(dataTypeName, dataSymbol);
		// System.out.println("Estrutura de dados '" + dataTypeName + "' registrada no
		// escopo global.");
	}
}
