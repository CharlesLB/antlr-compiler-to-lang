package lang.parser;

import java.io.FileReader;
import java.util.HashMap;
import beaver.Symbol;
import beaver.Parser;
import lang.ast.*;
import lang.parser.*;

public class TestUnique {
	public TestUnique(String arq, HashMap<String, Integer> h) {
		try {
			LangLexer input = new LangLexer(new FileReader(arq));
			LangParser parser = new LangParser();
			StmtList result = (StmtList) parser.parse(input);

			System.out.println("Parsado!");
			System.out.println(result.toString());

			System.out.println("--------- Executando ---------");
			result.interpret(h);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
