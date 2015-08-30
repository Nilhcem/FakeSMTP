package com.nilhcem.fakesmtp.model;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidHostException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI presentation model of the application.
 * <p>
 * The essence of a Presentation Model is of a fully self-contained class that represents all the data
 * and behavior of the UI window, but without any of the controls used to render that UI on the screen.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 * @see <a href="link">http://martinfowler.com/eaaDev/PresentationModel.html</a>
 */
public enum UIModel {
	INSTANCE;

	private boolean started = false; // server is not started by default
	private String portStr;
	private String hostStr;
	private int nbMessageReceived = 0;
	private String savePath = I18n.INSTANCE.get("emails.default.dir");
	private final Map<Integer, String> listMailsMap = new HashMap<Integer, String>();
	private List<String> relayDomains;

	UIModel() {
	}

	/**
	 * Happens when a user clicks on the start button.
	 * <p>
	 * This method will notify the {@code SMTPServerHandler} to start the server.
	 * </p>
	 *
	 * @throws InvalidPortException when the port is invalid.
	 * @throws BindPortException when the port cannot be bound.
	 * @throws OutOfRangePortException when the port is out of range.
	 * @throws InvalidHostException when the address cannot be resolved.
	 * @throws RuntimeException when an unknown exception happened.
	 */
	public void toggleButton() throws BindPortException, OutOfRangePortException, InvalidPortException, InvalidHostException {
		if (started) {
			// Do nothing. We can't stop the server. User has to quit the app (issue with SubethaSMTP)
		} else {
			try {
				int port = Integer.parseInt(portStr);
				InetAddress host = null;
				if (hostStr != null && !hostStr.isEmpty()) {
					host = InetAddress.getByName(hostStr);
				}

				SMTPServerHandler.INSTANCE.startServer(port, host);
			} catch (NumberFormatException e) {
				throw new InvalidPortException(e);
			} catch	(UnknownHostException e) {
				throw new InvalidHostException(e, hostStr);
			}
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

	public void setPort(String port) {
		this.portStr = port;
	}

	public void setHost(String host) {
		this.hostStr = host;
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

	public List<String> getRelayDomains() {
		return relayDomains;
	}

	public void setRelayDomains(List<String> relayDomains) {
		this.relayDomains = relayDomains;
	}
}
