package com.nilhcem.fakesmtp.log;

public interface ILogObservable {
	void addObserver(ILogObserver observer);
	void removeObserver(ILogObserver observer);
	void notifyObservers(String log);
}
