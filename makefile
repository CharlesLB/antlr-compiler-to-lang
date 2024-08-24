compile: genparser lang/LangCompiler.java 
	javac -d bin -cp ./lib/antlr-4.8-complete.jar lang/ast/*.java lang/parser/*.java lang/LangCompiler.java 

genparser: lang/parser/Lang.g4
	java -jar ./lib/antlr-4.8-complete.jar lang/parser/Lang.g4

run: compile genparser
	java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler -bs

clean:
	rm -R lang/parser/*Listener.java lang/parser/langLexer* lang/parser/langParser.java lang/parser/lang.interp lang/parser/lang.tokens
	find . -type f -name "*.class" -delete
	find . -type f -name "*.java~" -delete
