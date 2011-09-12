package com.nilhcem.fakesmtp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

// Singleton
public enum SMTPServerHandler {
	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(SMTPServerHandler.class);
	private final MailListener myListener = new MailListener();
	private final SMTPServer smtpServer;

	private SMTPServerHandler() {
		smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(myListener));
	}

	// throws java.lang.RuntimeException:  java.net.BindException "Permission denied"
	// throws java.lang.IllegalArgumentException: port out of range:
	public void startServer(int port) {
		LOGGER.debug("Starting server on port {}", port);
		smtpServer.setPort(port);
		smtpServer.start();
	}

	// Stops the server. If the server is not started, does nothing.
	public void stopServer() {
		if (smtpServer.isRunning()) {
			LOGGER.debug("Stopping server");
			smtpServer.stop();
		}
	}

	public MailListener getSMTPListener() {
		return myListener;
	}
}
