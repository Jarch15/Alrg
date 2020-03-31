package com.elliemae.alrg.exceptions;

public class ElementNotDisplayedException extends RuntimeException {
	
	public ElementNotDisplayedException(String message) {
        super(message);
    }

    public ElementNotDisplayedException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
