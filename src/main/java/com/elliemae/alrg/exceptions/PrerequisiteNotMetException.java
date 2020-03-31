package com.elliemae.alrg.exceptions;

public class PrerequisiteNotMetException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrerequisiteNotMetException(String message) {
        super(message);
    }

    public PrerequisiteNotMetException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
