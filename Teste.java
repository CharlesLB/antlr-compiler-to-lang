
import lang.*;

import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Teste {

	public static void main(String args[]) throws Exception {
		// Create a ANTLR CharStream from a file
		CharStream stream = CharStreams.fromFileName(args[0]);
		// create a lexer that feeds off of stream
		langLexer lex = new langLexer(stream);
		// create a buffer of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lex);
		// create a parser that feeds off the tokens buffer
		langParser parser = new langParser(tokens);
		// tell ANTLR to does not automatically build an AST
		parser.setBuildParseTree(false);

		Node ast = parser.prog().ast;
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		ast.interpret(m);
	}
}
