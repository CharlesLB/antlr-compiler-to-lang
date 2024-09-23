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

// java -cp ./bin:./lib/antlr-4.8-complete.jar lang.App <MODE> <PATH> <VERBOSE>

java -cp ./bin:./lib/antlr-4.8-complete.jar lang.App interpreter ./samples/semantic/true/teste0.lan 
```
