package com.example.book.response;

import lombok.Data;

@Data
public class DeleteBookResponse {
	
	private boolean success;
	private String message;
	
	public DeleteBookResponse(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
}
