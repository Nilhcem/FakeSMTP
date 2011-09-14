package com.nilhcem.fakesmtp.model;

import java.util.HashMap;
import java.util.Map;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

// UI Presentation model
//The essence of a Presentation Model is of a fully self-contained class that represents
//all the data and behavior of the UI window, but without any of the controls used to render that UI on the screen
//@see http://martinfowler.com/eaaDev/PresentationModel.html
public enum UIModel {
	INSTANCE;

	private boolean started = false; // server is not started by default
	private String portStr; // TODO: Change
	private long nbMessageReceived = 0;
	private String savePath = Configuration.INSTANCE.get("emails.default.dir");
	private final Map<Integer, String> listMailsMap = new HashMap<Integer, String>();

	private UIModel() {
	}

	// throws RuntimeException
	public void toggleButton() throws InvalidPortException, BindPortException, OutOfRangePortException {
		if (started) {
			stopServer();
		} else {
			startServer();
		}
	}

	public boolean isStarted() {
		return started;
	}

	// throws RuntimeException
	private void startServer() throws InvalidPortException, BindPortException, OutOfRangePortException {
		int port;

		try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException e) {
			throw new InvalidPortException(e);
		}
		SMTPServerHandler.INSTANCE.startServer(port);
		started = true;
	}

	private void stopServer() {
		SMTPServerHandler.INSTANCE.stopServer();
		started = false;
	}

	public String getPort() {
		return portStr;
	}

	public void setPort(String port) {
		this.portStr = port;
	}

	public long getNbMessageReceived() {
		return nbMessageReceived;
	}

	public void setNbMessageReceived(long nbMessageReceived) {
		this.nbMessageReceived = nbMessageReceived;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Map<Integer, String> getListMailsMap() {
		return listMailsMap;
	}
}
