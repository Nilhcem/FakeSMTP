package com.nilhcem.fakesmtp.log;

import java.util.Observable;

/**
 * Provides an observable object to notify the {@code LogsPane} object when a new log is received.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class SMTPLogsObservable extends Observable {
	/**
	 * Notify the {@code LogsPane} object when a new log is received.
	 *
	 * @param arg a String representing the received log.
	 */
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}
