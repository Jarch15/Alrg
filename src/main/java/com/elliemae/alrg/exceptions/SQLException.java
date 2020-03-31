package com.elliemae.alrg.exceptions;

public class SQLException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SQLException(String message) {
        super(message);
    }

    public SQLException(String message, Throwable throwable) {
        super(message, throwable);
    }	

}
