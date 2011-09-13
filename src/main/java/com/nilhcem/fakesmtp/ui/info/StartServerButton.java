package com.nilhcem.fakesmtp.ui.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public final class StartServerButton {
	private static final String START_SERVER_STR = "Start server";
	private static final String STOP_SERVER_STR = "Stop server";

	private final JButton button = new JButton(StartServerButton.START_SERVER_STR);

	public StartServerButton() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIModel.INSTANCE.toggleButton();
					switchText();
				} catch (InvalidPortException ipe) {
					displayError(String.format("Can't start SMTP Server.%n\"Listening port\" is invalid."));
				}
				catch (BindPortException bpe) {
					displayError(String.format("Can't start SMTP Server.%nMake sure no other program is listening on port %d.", bpe.getPort()));
				}
				catch (OutOfRangePortException orpe) {
					displayError(String.format("Can't start SMTP Server.%nPort %d is out of range.", orpe.getPort()));
				}
				catch (RuntimeException re) {
					displayError(String.format("Error starting SMTP Server:%n%s.", re.getMessage()));
				}
			}
		});
	}

	private void switchText() {
		button.setText(UIModel.INSTANCE.isStarted() ? StartServerButton.STOP_SERVER_STR : StartServerButton.START_SERVER_STR);
	}

	public JButton get() {
		return button;
	}

	// Displays a message dialog containing the error specified in parameter.
	private void displayError(String error) {
		JOptionPane.showMessageDialog(button.getParent(), error, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
