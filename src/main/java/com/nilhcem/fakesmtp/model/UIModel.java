package com.nilhcem.fakesmtp.model;

import java.util.HashMap;
import java.util.Map;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

/**
 * UI presentation model of the application.
 * <p>
 * The essence of a Presentation Model is of a fully self-contained class that represents all the data
 * and behavior of the UI window, but without any of the controls used to render that UI on the screen.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 * @see "http://martinfowler.com/eaaDev/PresentationModel.html"
 */
public enum UIModel {
	INSTANCE;

	private boolean started = false; // server is not started by default
	private String portStr;
	private int nbMessageReceived = 0;
	private String savePath = I18n.INSTANCE.get("emails.default.dir");
	private final Map<Integer, String> listMailsMap = new HashMap<Integer, String>();

	private UIModel() {
	}

	/**
	 * Happens when a user click on the start / stop button.
	 * <p>
	 * This method will notify the {@code SMTPServerHandler} to start and stop the server.
	 * </p>
	 *
	 * @throws InvalidPortException when the port is invalid.
	 * @throws BindPortException when the port cannot be bound.
	 * @throws OutOfRangePortException when the port is out of range.
	 * @throws RuntimeException when an unknown exception happened.
	 */
	public void toggleButton() throws BindPortException, OutOfRangePortException, InvalidPortException {
		if (started) {
			SMTPServerHandler.INSTANCE.stopServer();
		} else {
			int port;

			try {
				port = Integer.parseInt(portStr);
			} catch (NumberFormatException e) {
				throw new InvalidPortException(e);
			}
			SMTPServerHandler.INSTANCE.startServer(port);
		}
		started = !started;
	}

	/**
	 * Returns {@code true} if the server is started.
	 *
	 * @return {@code true} if the server is started.
	 */
	public boolean isStarted() {
		return started;
	}

	public String getPort() {
		return portStr;
	}

	public void setPort(String port) {
		this.portStr = port;
	}

	public int getNbMessageReceived() {
		return nbMessageReceived;
	}

	public void setNbMessageReceived(int nbMessageReceived) {
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
