package com.nilhcem.fakesmtp.gui.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.nilhcem.fakesmtp.server.MailSaver;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public final class ClearAllButton extends Observable implements Observer {
	private final JButton button = new JButton("Clear all");

	public ClearAllButton() {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(button.getParent(),
						"Do you want to delete the saved emails?", "Clear all",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				synchronized (SMTPServerHandler.INSTANCE.getMailSaver().getLock()) {
				    // Note: Should do that before calling observers, since observers will clean the model.
					if (answer == JOptionPane.YES_OPTION) {
						SMTPServerHandler.INSTANCE.getMailSaver().deleteEmails();
					}
				    setChanged();
				    notifyObservers();
					button.setEnabled(false);
				}
			}
		});
		button.setEnabled(false);
	}

	public JButton get() {
		return button;
	}

	@Override
	public void update(Observable o, Object arg) {
		if ((o instanceof MailSaver) && (!button.isEnabled())) {
			button.setEnabled(true);
		}
	}
}
