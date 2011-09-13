package com.nilhcem.fakesmtp.core.exception;

class AbstractPortException extends Exception {
	private static final long serialVersionUID = 9011196541962512429L;

	/* package-private */
	final int port;

	public AbstractPortException(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}
}
