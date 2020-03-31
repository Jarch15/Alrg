package com.elliemae.alrg.exceptions;

public class NotSupportedException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
