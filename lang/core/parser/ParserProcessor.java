package lang.core.parser;

import org.antlr.v4.runtime.*;
import lang.core.ast.*;
import lang.utils.ThrowingError;

public class ParserProcessor {

    public static Node parserByTokens(CommonTokenStream tokens) throws Exception {
        LangParser parser = new LangParser(tokens);
        parser.removeErrorListeners();

        parser.addErrorListener(ThrowingError.INSTANCE);

        Node ast = parser.prog().ast;

        if (ast == null) {
            throw new Exception("AST is null");
        }

        return ast;
    }
}
