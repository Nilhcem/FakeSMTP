package com.nilhcem.fakesmtp.gui.info;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.MailSaver;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public final class NbReceivedLabel implements Observer {
	private final JLabel nbReceived = new JLabel("0");

	public NbReceivedLabel() {
		// Set bold font
		Font boldFont = new Font(nbReceived.getFont().getName(), Font.BOLD, nbReceived.getFont().getSize());
		nbReceived.setFont(boldFont);

		// Add observer
		SMTPServerHandler.INSTANCE.getEmailSaver().addObserver(this);
	}

	public JLabel get() {
		return nbReceived;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MailSaver) {
			UIModel model = UIModel.INSTANCE;
			long countMsg = model.getNbMessageReceived() + 1;
			model.setNbMessageReceived(countMsg);
			nbReceived.setText(Long.toString(countMsg));
		}
	}
}
