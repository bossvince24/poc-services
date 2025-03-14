package com.example.book.exception;

public class AuthorNotFoundException extends RuntimeException {

	public AuthorNotFoundException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
