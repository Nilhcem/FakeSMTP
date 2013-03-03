package com.nilhcem.fakesmtp.server;

import java.util.ArrayList;
import java.util.List;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

/**
 * The factory interface for creating authentication handlers.
 *
 * @author jasonpenny
 * @since 1.2
 */
/*package*/ final class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {
	private static final String LOGIN_MECHANISM = "LOGIN";

	@Override
	public AuthenticationHandler create() {
		return new SMTPAuthHandler();
	}

	@Override
	public List<String> getAuthenticationMechanisms() {
		List<String> result = new ArrayList<String>();
		result.add(SMTPAuthHandlerFactory.LOGIN_MECHANISM);
		return result;
	}
}
