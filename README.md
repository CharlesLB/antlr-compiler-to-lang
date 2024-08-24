<h1 align="center">
    <b> Java Compiler </b> 
</h1>

<p align="center">
  <img alt="Language" src="https://img.shields.io/badge/language-Java-brightgreen">
  <img alt="Concept" src="https://img.shields.io/badge/concept-Compilers-brightgreen">
</p>

# Descrição

Este trabalho foi desenvolvido como parte da disciplina DCC045 - Teoria dos compiladores - UFJF, ministrada pelo professor Leonardo Vieira dos Santos Reis.

## Integrantes

- Nome: Charles Lelis Braga -- Matrícula: 202035015
- Nome: Gabriella Carvalho -- Matrícula: 202165047AC

## Informações

- Java version: Foi testado na 21.0.2 e na 17

### How to run

```bash
java -jar ./lib/jflex-full-1.8.2.jar lang/parser/lang.flex

java -jar ./lib/beaver-cc-0.9.11.jar -T lang/parser/lang.grammar

javac -cp .:./lib/beaver-cc-0.9.11.jar lang/LangCompiler.java

java -cp .:./lib/beaver-cc-0.9.11.jar lang/LangCompiler -(digite a opção que desejar -> teste unitário é -u)
```

Auxilio:
java -jar jflex-full-1.8.2.jar lang/parser/lang.flex ; java -jar beaver-cc-0.9.11.jar -T lang/parser/lang.grammar ; javac -cp .:beaver-cc-0.9.11.jar lang/LangCompiler.java
