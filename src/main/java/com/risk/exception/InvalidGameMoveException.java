package com.risk.exception;

/**
 * @author rahul
 * This class is used to handle Invalid Game Move exceptions.
 * @version 1.0.0
 *
 */
public class InvalidGameMoveException extends Exception {
	
	/**
	 * The @serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message to be printed as exception
	 */
	public InvalidGameMoveException(String message) {
		super(message);
	}
}
