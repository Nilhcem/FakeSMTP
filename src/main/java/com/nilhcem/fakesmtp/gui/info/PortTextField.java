package com.nilhcem.fakesmtp.gui.info;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.UIModel;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Text field in which will be written the desired SMTP port.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class PortTextField extends Observable implements Observer {

	private final JTextField portTextField = new JTextField();

	/**
	 * Creates the port field object and adds a listener on change to alert the presentation model.
	 * <p>
	 * The default port's value is defined in the configuration.properties file.<br>
	 * Each time the port is modified, the port from the {@link UIModel} will be reset.
	 * </p>
	 */
	public PortTextField() {
		portTextField.setToolTipText(I18n.INSTANCE.get("porttextfield.tooltip"));
		portTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			private void warn() {
				UIModel.INSTANCE.setPort(portTextField.getText());
			}
		});

		portTextField.setText(Configuration.INSTANCE.get("smtp.default.port"));
		portTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers();
			}
		});
	}

	/**
	 * Returns the JTextField object.
	 *
	 * @return the JTextField object.
	 */
	public JTextField get() {
		return portTextField;
	}

	/**
	 * Sets the specified port in the text field only if this latter is not {@code null}.
	 *
	 * @param portStr the port to set.
	 */
	public void setText(String portStr) {
		if (portStr != null && !portStr.isEmpty()) {
			portTextField.setText(portStr);
		}
	}

	/**
	 * Enables or disables the port text field.
	 * <p>
	 * When the element will receive an action from the {@link StartServerButton} object,
	 * it will enable or disable the port, so that the user can't modify it
	 * when the server is already launched.
	 * </p>
	 *
	 * @param o the observable element which will notify this class.
	 * @param arg optional parameters (not used).
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof StartServerButton) {
			portTextField.setEnabled(!UIModel.INSTANCE.isStarted());
		}
	}
}
