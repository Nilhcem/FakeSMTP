package com.nilhcem.fakesmtp.mail;

import java.io.InputStream;

public interface IMailObservable {
	public void addObserver(IMailObserver observer);
	public void removeObserver(IMailObserver observer);
	public void notifyObservers(InputStream data);
}
