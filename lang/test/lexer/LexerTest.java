package lang.test.lexer;

import java.io.File;

import org.antlr.v4.runtime.CharStreams;

import org.antlr.v4.runtime.*;
import lang.core.parser.LangLexer;
import lang.core.parser.ThrowingError;
import lang.test.Test;
import lang.utils.Logger;

public class LexerTest extends Test {

    public LexerTest(String path) {
        super(path);
    }

    public String test(File file) throws Exception {
        try {
            CharStream stream = CharStreams.fromFileName(file.getAbsolutePath());

            LangLexer lexer = new LangLexer(stream);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingError.INSTANCE);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            tokens.fill();

            printTokensTable(tokens);

            return "Lexer test passed";
        } catch (Exception e) {
            throw e;
        }
    }

    private void printTokensTable(CommonTokenStream tokens) {
        if (!Logger.verbose) {
            return;
        }

        for (Token token : tokens.getTokens()) {
            Logger.log("Token: " + token.getText() + " - " + token.getType() + " - " + token.getLine() + ":"
                    + token.getCharPositionInLine() + "\n");
        }
    }

}
