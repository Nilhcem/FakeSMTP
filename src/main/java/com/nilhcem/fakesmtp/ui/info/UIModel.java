package com.nilhcem.fakesmtp.ui.info;

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

	private UIModel() {
	}

	public void toggleButton() throws InvalidPortException, BindPortException,
			OutOfRangePortException, RuntimeException {
		if (started) {
			stopServer();
		} else {
			startServer();
		}
	}

	public boolean isStarted() {
		return started;
	}

	private void startServer() throws InvalidPortException, BindPortException,
			OutOfRangePortException, RuntimeException {
		int port;

		try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException e) {
			throw new InvalidPortException();
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

	public long incrementNbMsgReceived() {
		return ++nbMessageReceived;
	}

	public long getNbMessageReceived() {
		return nbMessageReceived;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
