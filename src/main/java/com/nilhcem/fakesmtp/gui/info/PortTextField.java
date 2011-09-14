package com.nilhcem.fakesmtp.gui.info;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.model.UIModel;

public final class PortTextField implements Observer {
	private final JTextField portTextField = new JTextField();

	public PortTextField() {
		// Add listenener to onchange to alert presentation model
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
	}

	public JTextField get() {
		return portTextField;
	}

	// enables / disables textField
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof StartServerButton) {
			portTextField.setEnabled(!UIModel.INSTANCE.isStarted());
		}
	}
}
