package com.nilhcem.fakesmtp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;

// Starts and stops SMTP server
public enum SMTPServerHandler {
	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(SMTPServerHandler.class);
	private final MailListener myListener = new MailListener();
	private final SMTPServer smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(myListener));

	private SMTPServerHandler() {
	}

	// throws java.lang.RuntimeException:  java.net.BindException "Permission denied"
	// throws java.lang.IllegalArgumentException: port out of range:
	public void startServer(int port) throws BindPortException, OutOfRangePortException, RuntimeException {
		LOGGER.debug("Starting server on port {}", port);
		try {
			smtpServer.setPort(port);
			smtpServer.start();
		}
		catch (RuntimeException exception) {
			if (exception.getMessage().contains("BindException")) { // can't open port
				LOGGER.error("{}. Port {}", exception.getMessage(), port);
				throw new BindPortException(port);
			}
			else if (exception.getMessage().contains("out of range")) { // port out of range
				LOGGER.error("Port {} our of range.", port);
				throw new OutOfRangePortException(port);
			}
			else { // unknown error
				LOGGER.error("", exception);
				throw exception;
			}
		}
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
