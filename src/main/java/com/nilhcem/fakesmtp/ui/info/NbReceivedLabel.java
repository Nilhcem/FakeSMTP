package com.nilhcem.fakesmtp.ui.info;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import com.nilhcem.fakesmtp.server.MailListener;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public class NbReceivedLabel implements Observer {
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
	public void update(Observable o, Object arg) {
		if (o instanceof MailListener) {
			nbReceived.setText(Long.toString(UIModel.INSTANCE.incrementNbMsgReceived()));
		}
	}
}
