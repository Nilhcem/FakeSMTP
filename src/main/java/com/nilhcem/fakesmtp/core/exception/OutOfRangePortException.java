package com.nilhcem.fakesmtp.core.exception;

/**
 * Thrown if the SMTP port is out of range while trying to start the server.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class OutOfRangePortException extends AbstractPortException {
	private static final long serialVersionUID = -8357518994968551990L;

	public OutOfRangePortException(Exception e, int port) {
		super(e, port);
	}
}
