package com.hotel.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2469633224328936571L;

	// extra properties that you want to mange
	public ResourceNotFoundException() {
		super("Resource not found on server !!");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
