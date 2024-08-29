package lang.symbols;

import java.util.*;

import lang.ast.definitions.Fun;
import lang.ast.expressions.ID;

public class FunctionTable {
	private static FunctionTable instance;
	private HashMap<String, Fun> functions;

	public FunctionTable() {
		functions = new HashMap<>();
	}

	public static FunctionTable getInstance() {
		if (instance == null) {
			instance = new FunctionTable();
		}
		return instance;
	}

	public void addFunction(Fun function) {
		ID IDFunction = function.getName();
		functions.put(IDFunction.getName(), function);
	}

	public Fun getFunction(String name) {
		return functions.get(name);
	}

	public void print() {
		if (functions.isEmpty()) {
			System.out.println("Nenhuma função registrada na tabela de funções.");
		} else {
			System.out.println("Tabela de Funções:");
			for (Map.Entry<String, Fun> entry : functions.entrySet()) {
				System.out.println("Nome da Função: " + entry.getKey());
				System.out.println("Definição da Função: " + entry.getValue().toString());
				System.out.println("------------------------------");
			}
		}
	}
}
