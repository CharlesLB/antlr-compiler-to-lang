package lang.test.lexer;

import java.io.File;

import org.antlr.v4.runtime.*;
import lang.core.parser.LexerProcessor;
import lang.test.Test;
import lang.utils.Logger;

public class LexerTest extends Test {

    public LexerTest(String path) {
        super(path);
    }

    public void test(File file) throws Exception {
        try {
            CommonTokenStream tokens = LexerProcessor.getTokens(file);

            printTokensTable(tokens);

            return;
        } catch (Exception e) {
            Logger.error("\nLexer test failed: " + e.getMessage());
            throw e;
        }
    }

    private void printTokensTable(CommonTokenStream tokens) {
        if (!Logger.verbose) {
            return;
        }

        for (Token token : tokens.getTokens()) {
            Logger.log("\nToken: " + token.getText() + " - " + token.getType() + " - " + token.getLine() + ":"
                    + token.getCharPositionInLine());
        }
        System.out.println();
    }

}
