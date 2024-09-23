/*  Nome: Charles Lelis Braga - Matrícula: 202035015 */
/*  Nome: Gabriella Carvalho -- Matrícula: 202165047AC */
package lang.utils;

public class TypeMismatchException extends RuntimeException {
	public TypeMismatchException() {
		super();
	}

	public TypeMismatchException(String message) {
		super(message);
	}

	public TypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeMismatchException(Throwable cause) {
		super(cause);
	}
}
