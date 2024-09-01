package lang.ast.symbols;

import java.util.*;

import lang.ast.definitions.Fun;
import lang.ast.expressions.ID;

/**
 * Estrutura que armazena todas as funções do código.
 */
public class FunctionTable {
	private static FunctionTable instance;
	private HashMap<String, List<Fun>> functions;
	private Fun mainFunction;

	public FunctionTable() {
		functions = new HashMap<>();
	}

	public static FunctionTable getInstance() {
		if (instance == null) {
			instance = new FunctionTable();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}

	public void addFunction(Fun function) {
		ID IDFunction = function.getName();
		String functionName = IDFunction.getName();

		if (functionName.equals("main")) {
			if (mainFunction != null) {
				throw new RuntimeException("A função `main` já foi definida e só pode existir uma.");
			}
			mainFunction = function;
			return;
		}

		// Funções normais com suporte a sobrecarga
		functions.computeIfAbsent(functionName, k -> new ArrayList<>()).add(function);
	}

	public Fun getMainFunction() {
		return mainFunction;
	}

	// Obtém a função com base no nome e na assinatura
	public Fun getFunction(String name, List<String> paramTypes) {
		if (name.equals("main")) {
			return mainFunction;
		}

		List<Fun> overloadedFunctions = functions.get(name);
		if (overloadedFunctions == null) {
			return null;
		}

		for (Fun function : overloadedFunctions) {
			if (function.getParams().size() == paramTypes.size()) {
				boolean matches = true;
				for (int i = 0; i < paramTypes.size(); i++) {
					if (!function.getParams().get(i).getType().toString().equals(paramTypes.get(i))) {
						matches = false;
						break;
					}
				}
				if (matches) {
					return function; // Encontrou a função com a assinatura correspondente
				}
			}
		}
		return null; // Nenhuma função com a assinatura correspondente
	}

	public void print() {
		if (functions.isEmpty() && mainFunction == null) {
			System.out.println("Nenhuma função registrada na tabela de funções.");
		} else {
			System.out.println("Tabela de Funções:");
			if (mainFunction != null) {
				System.out.println("Nome da Função: main");
				System.out.println("Definição da Função: " + mainFunction.toString());
				System.out.println("------------------------------");
			}
			for (Map.Entry<String, List<Fun>> entry : functions.entrySet()) {
				System.out.println("Nome da Função: " + entry.getKey());
				for (Fun function : entry.getValue()) {
					System.out.println("Definição da Função: " + function.toString());
				}
				System.out.println("------------------------------");
			}
		}
	}
}
