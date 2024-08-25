package lang.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import lang.ast.SuperNode;

public class LangParserAdaptor implements ParseAdaptor {

    @Override
    public SuperNode parseFile(String path) {
        try {

            CharStream stream = CharStreams.fromFileName(path);

            System.out.println("Parsing file: " + path);

            LangLexer lexer = new LangLexer(stream);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            tokens.fill();

            for (Token token : tokens.getTokens()) {
                System.out.println("Token: " + token.getText());
            }

            System.out.println("\n\n\n");

            // LangParser parser = new LangParser(tokens);

            // ParseTree tree = parser.prog();

            // for (int i = 0; i < tokens.size(); i++) {
            // Token token = tokens.get(i);
            // System.out.println("Token: " + token.getText() + " " + token.getType());
            // }

            // System.out.println(tree.toStringTree(parser));

            return null;
        } catch (Exception e) {
            System.out.println("Error parsing file: " + e.getMessage());
            // e.printStackTrace();
            return null;
        }
    }
}
