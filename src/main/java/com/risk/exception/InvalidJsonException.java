package com.risk.exception;

/**
 * This class is used to handle Invalid map exceptions.
 * @author Vipul
 * @version 1.0.0
 *
 */
public class InvalidJsonException extends Exception {
	
	/**
	 * The @serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message to be printed as exception
	 */
	public InvalidJsonException(String message) {
		super(message);
	}
}
