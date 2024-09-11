package lang.ast.definitions;

import visitors.Visitor;
import lang.ast.Node;

public class Program extends Node {

	private Fun[] funcs; // Definições de funções
	private Data[] datas; // Definições de tipos de dados personalizados

	// Construtor que recebe funções e tipos de dados
	public Program(Fun[] funcs, Data[] datas) {
		this.funcs = funcs;
		this.datas = datas;
	}

	// Retorna o array de funções
	public Fun[] getFuncs() {
		return funcs;
	}

	// Retorna o array de tipos de dados
	public Data[] getDatas() {
		return datas;
	}

	// Método toString para representar o programa como texto
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		// Adiciona todas as definições de tipos de dados
		for (Data d : datas) {
			s.append(d.toString()).append("\n");
		}

		// Adiciona todas as definições de funções
		for (Fun f : funcs) {
			s.append(f.toString()).append("\n");
		}

		return s.toString();
	}

	// Método accept para o padrão Visitor
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
