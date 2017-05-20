package com.sample.stat.config;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sample.stat.util.StatResourceException;

@ControllerAdvice
public class StatResourceExceptionHandler {
	
	@ExceptionHandler(StatResourceException.class)
	public ResponseEntity<?> handleException(StatResourceException e) {
		HashMap<String, Object> errorMap = new HashMap<>();
		errorMap.put("message", e.getMessage());
		
		return ResponseEntity.status(e.getHttpStatus()).body(errorMap);
	}

}
