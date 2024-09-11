/* Nome: Charles Lelis Braga - Matrícula: 202035015 */
/* Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.ast.definitions;

import lang.ast.Node;

/**
 * Representa os comandos da linguagem.
 *
 * @cmd Comando
 * @Parser ‘{’ {cmd} ‘}’
 *
 * @cmd Condicionais
 * @Parser if ‘(’ exp ‘)’ cmd
 * @Parser if ‘(’ exp ‘)’ cmd else cmd
 *
 * @cmd Repetição
 * @Parser iterate ‘(’ exp ‘)’ cmd
 *
 * @cmd Leitura
 * @Parser read lvalue ‘;’
 *
 * @cmd Escrita
 * @Parser print exp ‘;’
 *
 * @cmd Retorno
 * @Parser return exp {‘,’ exp} ‘;’
 *
 * @cmd Atribuição
 * @Parser lvalue = exp ‘;’
 *
 * @cmd Chamada de função com variáveis de retorno
 * @Parser ID ‘(’ [exps] ‘)’ [‘<’ lvalue {‘,’ lvalue} ‘>’] ‘;’
 */
public abstract class Cmd extends Node {
	public Cmd() {

	}
}
