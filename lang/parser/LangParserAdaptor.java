package lang.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import lang.ast.SuperNode;

public class LangParserAdaptor implements ParseAdaptor {

    @Override
    public SuperNode parseFile(String path) {
        try {
            CharStream stream = CharStreams.fromFileName(path);

            LangLexer lexer = new LangLexer(stream);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            LangParser parser = new LangParser(tokens);

            ParseTree tree = parser.prog();

            System.out.println(tree.toStringTree(parser));

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
