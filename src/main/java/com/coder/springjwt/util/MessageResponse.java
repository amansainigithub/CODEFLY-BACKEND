package com.coder.springjwt.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MessageResponse<T> {
	private String message;

	private HttpStatus status;

	private T data;

	public MessageResponse(String message) {
		this.message = message;
	}

	public MessageResponse(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public MessageResponse(String message, T data) {
		this.message = message;
		this.data = data;
	}

	public MessageResponse() {
	}
}
