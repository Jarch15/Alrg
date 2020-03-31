package com.elliemae.alrg.exceptions;

public class BusinessRuleNotFoundException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessRuleNotFoundException(String message) {
        super(message);
    }

    public BusinessRuleNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
