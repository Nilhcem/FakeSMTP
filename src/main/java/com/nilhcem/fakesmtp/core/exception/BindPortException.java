package com.nilhcem.fakesmtp.core.exception;

public final class BindPortException extends AbstractPortException {
	private static final long serialVersionUID = -4019988153141714187L;

	public BindPortException(Exception e, int port) {
		super(e, port);
	}
}
