package com.nilhcem.fakesmtp.log;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

// Logback appender class
// Observable
public final class SMTPLogsAppender<E> extends AppenderBase<E> implements ILogObservable {
	private final List<ILogObserver> observers = new ArrayList<ILogObserver>();

	@Override
	protected void append(E event) {
		if (event instanceof ILoggingEvent) {
			ILoggingEvent loggingEvent = (ILoggingEvent)event;
			notifyObservers(loggingEvent.getFormattedMessage());
		}
	}

	@Override
	public void addObserver(ILogObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(ILogObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String log) {
		for (ILogObserver observer : observers) {
			observer.update(log);
		}
	}
}
