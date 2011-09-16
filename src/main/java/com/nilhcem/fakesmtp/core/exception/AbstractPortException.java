package com.nilhcem.fakesmtp.core.exception;

/**
 * Abstract class to simplify creation of exceptions due to a SMTP port error.
 *
 * @author Nilhcem
 * @since 1.0
 */
abstract class AbstractPortException extends Exception {
	private static final long serialVersionUID = 9011196541962512429L;
	private final int port;

	/**
	 * Copies the stack trace of the exception passed in parameter, and sets the port which caused the exception.
	 *
	 * @param e the exception we need to copy the stack trace from.
	 * @param port the selected port which was the cause of the exception.
	 */
	public AbstractPortException(Exception e, int port) {
		setStackTrace(e.getStackTrace());
		this.port = port;
	}

	/**
	 * Returns the port entered by the user.
	 * <p>
	 * Useful to know why the SMTP server could not start.
	 * </p>
	 *
	 * @return the port which caused the exception.
	 */
	public int getPort() {
		return port;
	}
}
