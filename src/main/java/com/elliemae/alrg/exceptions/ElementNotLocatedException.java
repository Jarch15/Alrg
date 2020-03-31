package com.elliemae.alrg.exceptions;

public class ElementNotLocatedException extends RuntimeException {
	
	public ElementNotLocatedException(String message) {
        super(message);
    }

    public ElementNotLocatedException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
