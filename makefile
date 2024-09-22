compile: genlexer genparser
	javac -d bin -cp ./lib/antlr-4.8-complete.jar lang/enums/*.java lang/utils/* lang/core/ast/*.java lang/test/visitor/*.java lang/core/ast/definitions/*.java lang/core/ast/expressions/*.java lang/core/ast/expressions/literals/*.java lang/core/ast/expressions/operators/*.java lang/core/ast/lvalue/*.java lang/core/ast/statements/commands/*.java lang/core/ast/statements/data/*.java lang/core/ast/types/*.java lang/core/ast/symbols/*.java lang/core/parser/*.java lang/*.java lang/test/*.java lang/test/visitor/symbols/*.java lang/test/visitor/context/*.java
 
genparser: lang/core/parser/LangParser.g4
	java -jar ./lib/antlr-4.8-complete.jar lang/core/parser/LangParser.g4

genlexer: lang/core/parser/LangLexer.g4
	java -jar ./lib/antlr-4.8-complete.jar lang/core/parser/LangLexer.g4

run: compile
	java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler $(MODE) $(FILE) $(LOGS)

clean:
	rm -R lang/core/parser/*Listener.java lang/core/parser/LangLexer.java lang/core/parser/LangParser.java lang/core/parser/*.interp lang/core/parser/*.tokens lang/core/parser/.antlr lang/core/parser/Lang.java || true
	find . -type f -name "*.class" -delete || true
	find . -type f -name "*.java~" -delete || true
