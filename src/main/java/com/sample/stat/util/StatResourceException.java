package com.sample.stat.util;

import org.springframework.http.HttpStatus;

public class StatResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public StatResourceException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public StatResourceException(HttpStatus httpStatus) {
		super();
		this.httpStatus = httpStatus;
	}
	
	
}
