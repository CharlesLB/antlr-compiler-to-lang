compile: genparser Teste.java
	javac -cp .:./lib/antlr-4.8-complete.jar Teste.java

genparser: parser/lang.g4
	java -jar ./lib/antlr-4.8-complete.jar parser/lang.g4

run: compile genparser
	java -cp .:./lib/antlr-4.8-complete.jar Teste $(filter-out $@,$(MAKECMDGOALS))

clean:
	rm -R parser/*Listener.java parser/langLexer* parser/langParser.java parser/lang.interp parser/lang.tokens
	find . -type f -name "*.class" -delete
	find . -type f -name "*~" -delete
