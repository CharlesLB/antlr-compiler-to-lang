package lang.symbols;

import java.util.*;

import lang.ast.definitions.Data;
import lang.ast.expressions.ID;

public class DataTable {
	private static DataTable instance;
	private HashMap<String, Data> dataMap;

	private DataTable() {
		dataMap = new HashMap<>();
	}

	public static DataTable getInstance() {
		if (instance == null) {
			instance = new DataTable();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}

	public void addData(Data data) {
		ID IDData = data.getID();
		dataMap.put(IDData.getName(), data);
	}

	public Data getData(String name) {
		return dataMap.get(name);
	}

	public Map<String, Data> getDataMap() {
		return dataMap;
	}

	public void print() {
		if (dataMap.isEmpty()) {
			System.out.println("Nenhuma estrutura de dados registrada na tabela de dados.");
		} else {
			System.out.println("Tabela de Estruturas de Dados:");
			for (Map.Entry<String, Data> entry : dataMap.entrySet()) {
				System.out.println("Nome da Estrutura de Dados: " + entry.getKey());
				System.out.println("Definição da Estrutura de Dados: " + entry.getValue().toString());
				System.out.println("------------------------------");
			}
		}
	}
}
