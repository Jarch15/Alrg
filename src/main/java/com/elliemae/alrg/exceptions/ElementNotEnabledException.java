package com.elliemae.alrg.exceptions;

public class ElementNotEnabledException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElementNotEnabledException(String message) {
        super(message);
    }

    public ElementNotEnabledException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
