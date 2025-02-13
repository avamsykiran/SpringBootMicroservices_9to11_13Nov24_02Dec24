package com.cts.adb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.adb.exception.ContactNotFoundException;
import com.cts.adb.exception.InvalidContactDetailsException;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

	private Logger logger;
	
	public GlobalExceptionHandlerAdvice() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@ExceptionHandler(ContactNotFoundException.class)
	public ResponseEntity<String> handleContactNotFoundException(ContactNotFoundException exp){
		logger.error(exp.getMessage(), exp);
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidContactDetailsException.class)
	public ResponseEntity<String> handleInvalidContactDetailsException(InvalidContactDetailsException exp){
		logger.error(exp.getMessage(), exp);
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerForUnhandledExceptions(Exception exp){
		logger.error(exp.getMessage(), exp);
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
