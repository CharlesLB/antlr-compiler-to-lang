compile: genparser genlex
	javac -cp .:beaver-rt-0.9.11.jar lang/LangCompiler.java

genparser: parser/lang.grammar
	java -jar beaver-cc-0.9.11.jar -T parser/lang.grammar

genlex: parser/lang.jflex genparser
	java -jar jflex-full-1.8.2.jar parser/lang.jflex

run: compile
	java -cp .:beaver-rt-0.9.11.jar Teste $(filter-out $@,$(MAKECMDGOALS))

clean:
	rm -R parser/MiniLang*.java parser/Terminals.java
	find . -type f -name "*.class" -delete
	find . -type f -name "*~" -delete
