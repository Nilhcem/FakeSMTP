package com.nilhcem.fakesmtp.log;

import java.util.Observable;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

// Logback appender class
// Observable
public final class SMTPLogsAppender<E> extends AppenderBase<E> {
	private SMTPLogsObservable observable = new SMTPLogsObservable();

	@Override
	protected void append(E event) {
		if (event instanceof ILoggingEvent) {
			ILoggingEvent loggingEvent = (ILoggingEvent)event;
			observable.notifyObservers(loggingEvent.getFormattedMessage());
		}
	}

	public Observable getObservable() {
		return observable;
	}
}
