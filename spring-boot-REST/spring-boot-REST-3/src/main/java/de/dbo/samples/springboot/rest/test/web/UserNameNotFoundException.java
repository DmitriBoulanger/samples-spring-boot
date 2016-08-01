package de.dbo.samples.springboot.rest.test.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4634298867672645590L;
    
	private String username;

	public UserNameNotFoundException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

}
