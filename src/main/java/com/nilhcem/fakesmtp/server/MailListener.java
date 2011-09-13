package com.nilhcem.fakesmtp.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.subethamail.smtp.helper.SimpleMessageListener;

// Listens to incoming emails and notifies observers classes when emails arrive.
public final class MailListener implements SimpleMessageListener, IMailObservable {
	private final List<IMailObserver> observers = new ArrayList<IMailObserver>();

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
		notifyObservers(data);
	}

	@Override
	public void addObserver(IMailObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IMailObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(InputStream data) {
		for (IMailObserver observer : observers) {
			observer.update(data);
		}
	}
}
