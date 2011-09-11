package com.nilhcem.fakesmtp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.mail.SMTPServerHandler;
import com.nilhcem.fakesmtp.ui.MainPanel.PanelActionEnum;

public class StartServerButton extends JButton implements ActionListener {
	private static final long serialVersionUID = -6775847462270276642L;
	private static final Logger logger = LoggerFactory.getLogger(StartServerButton.class);

	private static final String START_SERVER_STR = "Start server";
	private static final String STOP_SERVER_STR = "Stop server";

	public StartServerButton() {
		super(StartServerButton.START_SERVER_STR);
		setActionCommand(PanelActionEnum.START_SERVER.toString());
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();

		if (actionCommand.equals(PanelActionEnum.START_SERVER.toString())) {
			startServer();
		} else if (actionCommand.equals(PanelActionEnum.STOP_SERVER.toString())) {
			stopServer();
		}
	}

	// Starts server and changes button text and action
	private void startServer() {
		Integer port = getPort();

		if (port != null) {
			try {
				SMTPServerHandler.INSTANCE.startServer(port);
				setActionCommand(PanelActionEnum.STOP_SERVER.toString());
				setText(StartServerButton.STOP_SERVER_STR);
			} catch (RuntimeException exception) {
				if (exception.getMessage().contains("BindException")) { // can't open port
					logger.error("{}. Port {}", exception.getMessage(), port);
					displayError(String.format("Can't start SMTP Server.%nMake sure no other program is listening on port %d.", port));
				} else if (exception.getMessage().contains("out of range")) { // port out of range
					logger.error("Port {} our of range.", port);
					displayError(String.format("Can't start SMTP Server.%nPort %d is out of range.", port));
				}
				else { // unknown error
					logger.error("", exception);
					displayError(String.format("Error starting SMTP Server:%n%s.", exception.getMessage()));
				}
			}
		}
	}

	// Stops server and changes button text and action
	private void stopServer() {
		SMTPServerHandler.INSTANCE.stopServer();
		setActionCommand(PanelActionEnum.START_SERVER.toString());
		setText(StartServerButton.START_SERVER_STR);
	}

	// Gets port and displays error if port is invalid.
	private Integer getPort() {
		Integer port = null;

		try {
			port = Integer.parseInt(((MainPanel)getParent()).getPortTextValue());
		} catch (NumberFormatException e) {
			displayError(String.format("Can't start SMTP Server.%n\"Listening port\" is invalid."));
		}
		return port;
	}

	// Displays a message dialog containing the error specified in parameter.
	private void displayError(String error) {
		JOptionPane.showMessageDialog(getParent(), error, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
