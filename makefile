compile: genparser genlex
	javac -cp .:beaver-rt-0.9.11.jar lang/LangCompiler.java

genparser: lang/parser/lang.grammar
	java -jar beaver-cc-0.9.11.jar -T lang/parser/lang.grammar

genlex: lang/parser/lang.flex genparser
	java -jar jflex-full-1.8.2.jar lang/parser/lang.flex

run: compile
	java -cp .:beaver-rt-0.9.11.jar lang/LangCompiler $(filter-out $@,$(MAKECMDGOALS))

clean:
	rm -R lang/parser/Lang*.java lang/parser/Terminals.java
	find . -type f -name "*.class" -delete
	find . -type f -name "*~" -delete
