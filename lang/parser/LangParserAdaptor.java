package lang.parser;

import java.util.HashMap;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;
import lang.ast.*;
import lang.parser.LangLexer;
import lang.parser.LangParser;

public class LangParserAdaptor implements ParseAdaptor {

    @Override
    public SuperNode parseFile(String path) {
        try {

            CharStream stream = CharStreams.fromFileName(path);

            System.out.println("Parsing file: " + path);

            LangLexer lexer = new LangLexer(stream);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingError.INSTANCE);

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();

            // for (Token token : tokens.getTokens()) {
            // System.out.println("Token: " + token.getType() + " " + token.getText());
            // }

            // System.out.println("\n\n\n");

            LangParser parser = new LangParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingError.INSTANCE);

            try {
                parser.setBuildParseTree(false);

                // for (int i = 0; i < tokens.size(); i++) {
                // Token token = tokens.get(i);
                // System.out.println("Token: " + token.getText() + " " + token.getType());
                // }

                // System.out.println(tree.toStringTree(parser));

                Node ast = parser.prog().ast;

                if (ast == null) {
                    System.err.println("Parsing failed for file: " + path);
                    return null;
                }
                System.out.println(ast);

                HashMap<String, Object> m = new HashMap<String, Object>();
                Object result = ast.interpret(m);

                System.out.println("Resultado no parser: " + result.toString());

                return ast;
            } catch (ParseCancellationException e) {
                System.err.println("Erro de parsing: " + e.getMessage());
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error parsing file: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }
    }
}
