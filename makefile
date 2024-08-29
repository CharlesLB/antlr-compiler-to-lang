compile: genlexer genparser
	javac -d bin -cp ./lib/antlr-4.8-complete.jar lang/ast/*.java lang/ast/definitions/*.java lang/ast/expressions/*.java lang/ast/expressions/literals/*.java lang/ast/expressions/operators/*.java lang/ast/lvalue/*.java lang/ast/statements/*.java lang/ast/statements/commands/*.java lang/ast/statements/data/*.java lang/ast/types/*.java lang/parser/*.java lang/LangCompiler.java 

genparser: lang/parser/LangParser.g4
	java -jar ./lib/antlr-4.8-complete.jar lang/parser/LangParser.g4

genlexer: lang/parser/LangLexer.g4
	java -jar ./lib/antlr-4.8-complete.jar lang/parser/LangLexer.g4

run: compile
	java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler -bs

clean:
	rm -R lang/parser/*Listener.java lang/parser/LangLexer.java lang/parser/LangParser.java lang/parser/*.interp lang/parser/*.tokens lang/parser/.antlr lang/parser/Lang.java || true
	find . -type f -name "*.class" -delete || true
	find . -type f -name "*.java~" -delete || true
