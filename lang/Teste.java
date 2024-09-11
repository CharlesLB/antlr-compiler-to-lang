package lang;

import lang.parser.*;
import lang.ast.*;
import visitors.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.File;
import java.io.IOException;

public class Teste {

	// Caminho para o diretório de testes
	private static String testDirectory = "testes/semantica/errado";
	private static File testFolder = new File(testDirectory);

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			// Se for fornecido um arquivo diretamente, roda o teste apenas para esse
			// arquivo
			runSingleTest(args[0]);
		} else {
			// Caso contrário, roda todos os testes no diretório de testes
			runAllTests();
		}
	}

	// Método para rodar um único teste em um arquivo fornecido
	public static void runSingleTest(String filePath) {
		try {
			CharStream stream = CharStreams.fromFileName(filePath);

			LangLexer lexer = new LangLexer(stream);

			// Adiciona o CustomErrorListener ao lexer para capturar erros de reconhecimento
			// de tokens
			CustomErrorListener errorListener = new CustomErrorListener();
			lexer.removeErrorListeners(); // Remove os listeners padrão do lexer
			lexer.addErrorListener(errorListener); // Adiciona o listener customizado ao lexer

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			LangParser parser = new LangParser(tokens);

			// Adiciona o CustomErrorListener para capturar erros de parsing
			parser.removeErrorListeners(); // Remove os listeners padrão do parser
			parser.addErrorListener(errorListener); // Adiciona o listener customizado ao parser

			parser.setBuildParseTree(false);
			Node ast = parser.prog().ast;

			// Verifica se houve erros de token ou parsing
			if (errorListener.hasError()) {
				System.out.println("Erro de parsing ou de reconhecimento de token no arquivo: " + filePath);
			} else {
				// Exibe a AST resultante se não houve erros
				System.out.println(ast.toString());
				// Executa o visitante de escopo (ScopeVisitor)
				// ast.accept(new ScopeVisitor());
			}

		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + filePath);
		} catch (ParseCancellationException e) {
			System.err.println("Erro de parsing no arquivo: " + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para rodar todos os testes no diretório especificado
	public static void runAllTests() {
		File[] testFiles;
		int passes = 0;
		int failures = 0;

		try {
			if (testFolder.isDirectory()) {
				testFiles = testFolder.listFiles();
				if (testFiles == null || testFiles.length == 0) {
					System.out.println("Nenhum arquivo de teste encontrado no diretório " + testFolder.getPath());
					return;
				}

				for (File testFile : testFiles) {
					String path = testFile.getPath();
					System.out.print("Testando " + path + filler(50 - path.length()) + "[");

					// Tenta rodar o parser no arquivo de teste
					if (parseAndTest(path)) {
						System.out.println(" OK ]");
						passes++;
					} else {
						System.out.println(" FALHOU ]");
						failures++;
					}
				}

				System.out.println("Total de acertos: " + passes);
				System.out.println("Total de erros: " + failures);

			} else {
				System.out.println("O caminho " + testFolder.getPath() + " não é um diretório ou não existe.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para analisar o arquivo e testar a AST
	public static boolean parseAndTest(String filePath) {
		try {
			CharStream stream = CharStreams.fromFileName(filePath);

			LangLexer lexer = new LangLexer(stream);

			// Adiciona o CustomErrorListener ao lexer para capturar erros de reconhecimento
			// de tokens
			CustomErrorListener errorListener = new CustomErrorListener();
			lexer.removeErrorListeners(); // Remove os listeners padrão do lexer
			lexer.addErrorListener(errorListener); // Adiciona o listener customizado ao lexer

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			LangParser parser = new LangParser(tokens);

			// Adiciona o CustomErrorListener para capturar erros de parsing
			parser.removeErrorListeners(); // Remove os listeners padrão do parser
			parser.addErrorListener(errorListener); // Adiciona o listener customizado ao parser

			parser.setBuildParseTree(false);
			Node ast = parser.prog().ast;

			// Verifica se houve erros de token ou parsing
			if (errorListener.hasError()) {
				return false; // Retorna false se houver erros
			}

			// Exibe a AST resultante (para depuração, pode ser removido)
			System.out.println(ast.toString());

			// Executa o ScopeVisitor, se necessário
			// ast.accept(new ScopeVisitor());

			return true; // Se não houver erro, retorna sucesso

		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo: " + filePath);
			return false;
		} catch (ParseCancellationException e) {
			System.err.println("Erro de parsing no arquivo: " + filePath);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Método auxiliar para adicionar espaços no output
	private static String filler(int n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n; i++) {
			s.append(" ");
		}
		return s.toString();
	}
}
