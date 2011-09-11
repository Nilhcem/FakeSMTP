package com.nilhcem.fakesmtp.mail;

import java.io.InputStream;

public interface IMailObservable {
	void addObserver(IMailObserver observer);
	void removeObserver(IMailObserver observer);
	void notifyObservers(InputStream data);
}
