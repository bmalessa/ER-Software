package de.protin.support.pr.domain.exception;

@SuppressWarnings("serial")
public class ValidationException extends Exception {
	
	public ValidationException() {
		super();
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
}
