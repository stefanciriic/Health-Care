package eng.it.stefan.ciric.exception;

public class EngineeringException extends Exception {
	private static final long serialVersionUID = 1L;

	public EngineeringException() {
		super();
	}

	public EngineeringException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EngineeringException(String message, Throwable cause) {
		super(message, cause);
	}

	public EngineeringException(String message) {
		super(message);
	}

	public EngineeringException(Throwable cause) {
		super(cause);
	}

}
