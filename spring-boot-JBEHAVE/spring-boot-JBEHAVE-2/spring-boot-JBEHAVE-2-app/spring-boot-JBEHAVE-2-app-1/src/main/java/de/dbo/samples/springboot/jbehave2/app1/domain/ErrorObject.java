package de.dbo.samples.springboot.jbehave2.app1.domain;

public class ErrorObject {

	private String message;
	
	
	public ErrorObject(String message) {
		super();
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
