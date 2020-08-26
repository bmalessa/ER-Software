package de.protin.support.pr.domain.validation;

public class ValidationItem {
	
	String message;
	
	public ValidationItem(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
