package lang;

import org.antlr.v4.runtime.*;

public class CustomErrorListener extends BaseErrorListener {
	private boolean hasError = false;

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
			int line, int charPositionInLine, String msg, RecognitionException e) {
		hasError = true; // Marca que um erro ocorreu
		System.err.println("Erro de sintaxe na linha " + line + ":" + charPositionInLine + " - " + msg);
	}

	public boolean hasError() {
		return hasError; // Retorna se um erro ocorreu
	}
}
