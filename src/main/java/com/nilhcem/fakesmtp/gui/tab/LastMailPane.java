package com.nilhcem.fakesmtp.gui.tab;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.server.MailSaver;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public final class LastMailPane implements Observer {
	private final JScrollPane lastMailPane = new JScrollPane();
	private final JTextArea lastMailArea = new JTextArea();

	public LastMailPane() {
		lastMailArea.setEditable(false);
		lastMailPane.getViewport().add(lastMailArea, null);
		SMTPServerHandler.INSTANCE.getEmailSaver().addObserver(this);
	}

	public JScrollPane get() {
		return lastMailPane;
	}

	@Override
	public synchronized void update(Observable o, Object data) {
		if (o instanceof MailSaver) {
			EmailModel model = (EmailModel) data;
			lastMailArea.setText(model.getEmailStr());
		}
	}
}
