package com.risk.exception;

public class InvalidGameMoveException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message to be printed as exception
	 */
	public InvalidGameMoveException(String message) {
		super(message);
	}
}
