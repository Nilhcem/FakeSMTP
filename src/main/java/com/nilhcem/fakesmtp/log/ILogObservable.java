package com.nilhcem.fakesmtp.log;

public interface ILogObservable {
	public void addObserver(ILogObserver observer);
	public void removeObserver(ILogObserver observer);
	public void notifyObservers(String log);
}
