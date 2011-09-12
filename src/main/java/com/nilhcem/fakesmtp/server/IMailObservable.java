package com.nilhcem.fakesmtp.server;

import java.io.InputStream;

public interface IMailObservable {
	void addObserver(IMailObserver observer);
	void removeObserver(IMailObserver observer);
	void notifyObservers(InputStream data);
}
