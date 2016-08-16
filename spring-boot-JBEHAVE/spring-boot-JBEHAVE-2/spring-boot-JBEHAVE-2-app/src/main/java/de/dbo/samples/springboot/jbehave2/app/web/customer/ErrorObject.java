package de.dbo.samples.springboot.jbehave2.app.web.customer;

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
