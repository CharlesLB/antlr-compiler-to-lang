<h1 align="center">
    <b> Java Compiler </b> 
</h1>

<p align="center">
    <img alt="Language" src="https://img.shields.io/badge/language-antlr-ff0">
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

Rode o comando:

```bash
make compile

java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler -bs
java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler -bsm

#  ou

make run # java -cp ./bin:./lib/antlr-4.8-complete.jar lang.LangCompiler -bs
```

### Como rodar apenas o Parser

Caso seja de interesse testar apenas o Parser, basta:

1. Comente a chamada da classe `InterpreterRunner`
2. Altere os diretórios no TestParse.java.

Vale um adendo que o Parser deve funcionar com todos os diretórios de sucesso, inclusive o semântico.


