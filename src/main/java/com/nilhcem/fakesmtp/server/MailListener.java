package com.nilhcem.fakesmtp.server;

import java.io.IOException;
import java.io.InputStream;
import org.subethamail.smtp.helper.SimpleMessageListener;

/**
 * Listens to incoming emails and redirects them to the {@code MailSaver} object.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MailListener implements SimpleMessageListener {
	private final MailSaver saver;
	private float delay;

	/**
	 * Creates the listener.
	 *
	 * @param saver a {@code MailServer} object used to save emails and notify components.
	 */
	public MailListener(MailSaver saver) {
		this.saver = saver;
	}

	/**
	 * Accepts all kind of email <i>(always return true)</i>.
	 * <p>
	 * Called once for every RCPT TO during a SMTP exchange.<br>
     * Each accepted recipient will result in a separate deliver() call later.
     * </p>
     * But first delay (if delay is more than 0).
     * @see setDelay(float)
     *
	 * @param from the user who send the email.
	 * @param recipient the recipient of the email.
	 * @return always return {@code true}
	 */
	public boolean accept(String from, String recipient) {
		
		try {
			Thread.sleep(  (long) ( delay * 1000 ) );
		} catch (InterruptedException e) {
			System.out.println("Delay (sleep) is interrupted.");
			Thread.currentThread().interrupt();
		}
		return true;
	}

    /**
     * Receives emails and forwards them to the {@link MailSaver} object.
     */
	@Override
	public void deliver(String from, String recipient, InputStream data) throws IOException {
		saver.saveEmailAndNotify(from, recipient, data);
	}

	/**
	 * @param delay number of seconds to delay acceptance
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}
}
