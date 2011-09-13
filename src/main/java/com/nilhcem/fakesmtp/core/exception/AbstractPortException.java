package com.nilhcem.fakesmtp.core.exception;

class AbstractPortException extends Exception {
	private static final long serialVersionUID = 9011196541962512429L;
	private final int port;

	public AbstractPortException(Exception e, int port) {
		setStackTrace(e.getStackTrace());
		this.port = port;
	}

	public int getPort() {
		return port;
	}
}
