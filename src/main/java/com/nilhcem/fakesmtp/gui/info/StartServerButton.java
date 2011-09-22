package com.nilhcem.fakesmtp.gui.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.model.UIModel;

/**
 * Button to start / stop the SMTP server.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class StartServerButton extends Observable {
	private final I18n i18n = I18n.INSTANCE;

	private final JButton button = new JButton(i18n.get("startsrv.start"));

	/**
	 * Creates a start / stop button to start and stop the SMTP server.
	 * <p>
	 * If the user selects a wrong port before starting the server, the method will display an error message.
	 * </p>
	 */
	public StartServerButton() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIModel.INSTANCE.toggleButton();
					toggleButton();
				} catch (InvalidPortException ipe) {
					displayError(String.format(i18n.get("startsrv.err.invalid")));
				} catch (BindPortException bpe) {
					displayError(String.format(i18n.get("startsrv.err.bound"), bpe.getPort()));
				} catch (OutOfRangePortException orpe) {
					displayError(String.format(i18n.get("startsrv.err.range"), orpe.getPort()));
				} catch (RuntimeException re) {
					displayError(String.format(i18n.get("startsrv.err.default"), re.getMessage()));
				}
			}
		});
	}

	/**
	 * Switches the text inside the button and calls the PortTextField observer to enable/disable the port field.
	 *
	 * @see PortTextField
	 */
	private void toggleButton() {
		String btnText;
		if (UIModel.INSTANCE.isStarted()) {
			btnText = i18n.get("startsrv.stop");
		} else {
			btnText = i18n.get("startsrv.start");
		}
		button.setText(btnText);
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
		JOptionPane.showMessageDialog(button.getParent(), error, i18n.get("startsrv.err.title"), JOptionPane.ERROR_MESSAGE);
	}
}
