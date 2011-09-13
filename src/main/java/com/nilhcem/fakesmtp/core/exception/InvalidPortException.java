package com.nilhcem.fakesmtp.core.exception;

public final class InvalidPortException extends Exception {
	private static final long serialVersionUID = -3964366344520192790L;

	public InvalidPortException(Exception e) {
		setStackTrace(e.getStackTrace());
	}
}
