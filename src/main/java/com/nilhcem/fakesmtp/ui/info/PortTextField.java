package com.nilhcem.fakesmtp.ui.info;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.nilhcem.fakesmtp.core.Configuration;

public final class PortTextField {
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
}
