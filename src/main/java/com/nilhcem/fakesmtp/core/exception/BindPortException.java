package com.nilhcem.fakesmtp.core.exception;

/**
 * Thrown if the SMTP port cannot be bound while trying to start the server.
 * <p>
 * A port cannot be bound...
 * </p>
 * <ul>
 *   <li>If it is already use by another application;</li>
 *   <li>If the user is not allowed to open it.<br>
 *   For example on Unix-like machines, we need to be root to open a port {@literal <} 1024.</li>
 * </ul>
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class BindPortException extends AbstractPortException {

	private static final long serialVersionUID = -4019988153141714187L;

	public BindPortException(Exception e, int port) {
		super(e, port);
	}
}
