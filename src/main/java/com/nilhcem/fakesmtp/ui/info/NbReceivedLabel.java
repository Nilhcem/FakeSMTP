package com.nilhcem.fakesmtp.ui.info;

import java.awt.Font;
import java.io.InputStream;
import javax.swing.JLabel;
import com.nilhcem.fakesmtp.server.IMailObserver;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public class NbReceivedLabel implements IMailObserver {
	private final JLabel nbReceived = new JLabel("0");

	public NbReceivedLabel() {
		// Set bold font
		Font boldFont = new Font(nbReceived.getFont().getName(), Font.BOLD, nbReceived.getFont().getSize());
		nbReceived.setFont(boldFont);

		// Add observer
		SMTPServerHandler.INSTANCE.getSMTPListener().addObserver(this);
	}

	public JLabel get() {
		return nbReceived;
	}

	@Override
	public void update(InputStream data) {
		nbReceived.setText(Long.toString(UIModel.INSTANCE.incrementNbMsgReceived()));
	}
}
