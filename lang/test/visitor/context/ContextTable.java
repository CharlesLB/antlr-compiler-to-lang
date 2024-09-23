/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.test.visitor.context;

import lang.test.visitor.symbols.Symbol;
import lang.test.visitor.symbols.SymbolTable;

public class ContextTable {

	private int context_level;

	private SymbolTable[] contexts;

	public ContextTable() {
		context_level = 0;
		contexts = new SymbolTable[8];
		contexts[context_level] = new SymbolTable();
	}

	public int getLevel() {
		return context_level;
	}

	public void put(String key, Symbol value) {
		contexts[context_level].put(key, value);
	}

	public int push() {
		++context_level;
		if (context_level == contexts.length) {
			SymbolTable[] aux = new SymbolTable[2 * contexts.length];
			for (int i = 0; i < contexts.length; ++i)
				aux[i] = contexts[i];
			contexts = aux;
		}
		contexts[context_level] = new SymbolTable();
		return context_level;
	}

	public int pop() {
		context_level--;
		return context_level;
	}

	public Pair<Symbol, Integer> search(String key) {
		int level = context_level;
		while (level >= 0) {
			Symbol s = contexts[level].lookup(key);
			if (s != null)
				return new Pair<Symbol, Integer>(s, level);
			level--;
		}
		return null;
	}

	public void printContexts() {
		for (int i = 0; i <= context_level; i++) {
			System.out.println("Escopo Nível " + i + ":");
			System.out.println(contexts[i]);
		}
	}
}
