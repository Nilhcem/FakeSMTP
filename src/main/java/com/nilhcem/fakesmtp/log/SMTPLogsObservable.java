package com.nilhcem.fakesmtp.log;

import java.util.Observable;

public class SMTPLogsObservable extends Observable {
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}
