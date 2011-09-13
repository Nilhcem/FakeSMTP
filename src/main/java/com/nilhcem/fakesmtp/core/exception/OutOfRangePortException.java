package com.nilhcem.fakesmtp.core.exception;

public final class OutOfRangePortException extends AbstractPortException {
	private static final long serialVersionUID = -8357518994968551990L;

	public OutOfRangePortException(int port) {
		super(port);
	}
}
