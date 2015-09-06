package com.nilhcem.fakesmtp.gui.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.core.exception.*;
import com.nilhcem.fakesmtp.model.UIModel;

/**
 * Button to start the SMTP server.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class StartServerButton extends Observable implements Observer {
	private final I18n i18n = I18n.INSTANCE;

	private final JButton button = new JButton(i18n.get("startsrv.start"));

	/**
	 * Creates a start button to start the SMTP server.
	 * <p>
	 * If the user selects a wrong port before starting the server, the method will display an error message.
	 * </p>
	 */
	public StartServerButton() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleButton();
			}
		});
	}

	/**
	 * Switches the text inside the button and calls the PortTextField observer to enable/disable the port field.
	 *
	 * @see PortTextField
	 */
	public void toggleButton() {
		try {
			UIModel.INSTANCE.toggleButton();
		} catch (InvalidHostException ihe) {
			displayError(String.format(i18n.get("startsrv.err.invalidHost"), ihe.getHost()));
		} catch (InvalidPortException ipe) {
			displayError(String.format(i18n.get("startsrv.err.invalidPort")));
		} catch (BindPortException bpe) {
			displayError(String.format(i18n.get("startsrv.err.bound"), bpe.getPort()));
		} catch (OutOfRangePortException orpe) {
			displayError(String.format(i18n.get("startsrv.err.range"), orpe.getPort()));
		} catch (RuntimeException re) {
			displayError(String.format(i18n.get("startsrv.err.default"), re.getMessage()));
		}

		if (UIModel.INSTANCE.isStarted()) {
			button.setText(i18n.get("startsrv.started"));
			button.setEnabled(false);
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns the JButton object.
	 *
	 * @return the JButton object.
	 */
	public JButton get() {
		return button;
	}

	/**
	 * Displays a message dialog displaying the error specified in parameter.
	 *
	 * @param error a string representing the error which will be displayed in a message dialog.
	 */
	private void displayError(String error) {
		JOptionPane.showMessageDialog(button.getParent(), error,
			String.format(i18n.get("startsrv.err.title"), Configuration.INSTANCE.get("application.name")),
			JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof PortTextField) {
			toggleButton();
		}
	}
}
