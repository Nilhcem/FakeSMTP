package com.nilhcem.fakesmtp.ui;

import java.awt.Font;
import java.io.InputStream;

import javax.swing.JLabel;

import com.nilhcem.fakesmtp.mail.IMailObserver;
import com.nilhcem.fakesmtp.mail.SMTPServerHandler;

public final class NbReceivedLabel extends JLabel implements IMailObserver {
	private static final long serialVersionUID = 6438269208461377412L;
	private static long nbMsgReceived = 0;

	// Initializes the number of received mails as 0 and add observer to receive notification when we got a message
	public NbReceivedLabel() {
		super("0");

		// Set bold font
		Font boldFont = new Font(getFont().getName(), Font.BOLD, getFont().getSize());
		setFont(boldFont);

		SMTPServerHandler.INSTANCE.getSMTPListener().addObserver(this);
	}

	@Override
	public synchronized void update(InputStream data) {
		setText(Long.toString(++nbMsgReceived));
	}

	public synchronized long getNbMsgReceived(){
		return nbMsgReceived;
	}
}
