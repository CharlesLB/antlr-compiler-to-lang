package lang.utils;

public class TypeMismatchException extends RuntimeException {

	// Construtor padrão
	public TypeMismatchException() {
		super();
	}

	// Construtor com mensagem
	public TypeMismatchException(String message) {
		super(message);
	}

	// Construtor com mensagem e causa
	public TypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	// Construtor com causa
	public TypeMismatchException(Throwable cause) {
		super(cause);
	}
}
