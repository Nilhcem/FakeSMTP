package com.nilhcem.fakesmtp.server;

import java.io.IOException;
import java.io.InputStream;
import org.subethamail.smtp.helper.SimpleMessageListener;

// Listens to incoming emails and notifies observers classes when emails arrive.
public final class MailListener implements SimpleMessageListener {
	private final MailSaver saver;

	public MailListener(MailSaver saver) {
		this.saver = saver;
	}

    /**
     * Called once for every RCPT TO during a SMTP exchange.
     * Each accepted recipient will result in a separate deliver() call later.
     */
	// Always return true;
	public boolean accept(String from, String recipient) {
		return true;
	}

	@Override
	public void deliver(String from, String recipient, InputStream data) throws IOException {
		saver.saveEmailAndNotify(from, recipient, data);
	}
}
