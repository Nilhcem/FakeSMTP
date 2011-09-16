package com.nilhcem.fakesmtp.gui.tab;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nilhcem.fakesmtp.gui.info.ClearAllButton;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.server.MailSaver;

/**
 * Scrolled text area where will be displayed the last received email.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class LastMailPane implements Observer {
	private final JScrollPane lastMailPane = new JScrollPane();
	private final JTextArea lastMailArea = new JTextArea();

	/**
	 * Creates the text area and disables the possibility to edit it.
	 */
	public LastMailPane() {
		lastMailArea.setEditable(false);
		lastMailPane.getViewport().add(lastMailArea, null);
	}

	/**
	 * Returns the JScrollPane object.
	 *
	 * @return the JScrollPane object.
	 */
	public JScrollPane get() {
		return lastMailPane;
	}

	/**
	 * Updates the content of the text area.
	 * <p>
	 * This method will be called by an observable element.
	 * <ul>
	 *   <li>If the observable is a {@link MailSaver} object, the text area will contain the content of the last received email;</li>
	 *   <li>If the observable is a {@link ClearAllButton} object, the text area will be cleared.</li>
	 * </ul>
	 * </p>
	 *
	 * @param o the observable element which will notify this class.
	 * @param data optional parameters (an {@code EmailModel} object, for the case of a {@code MailSaver} observable).
	 */
	@Override
	public synchronized void update(Observable o, Object data) {
		if (o instanceof MailSaver) {
			EmailModel model = (EmailModel) data;
			lastMailArea.setText(model.getEmailStr());
		} else if (o instanceof ClearAllButton) {
			lastMailArea.setText("");
		}
	}
}
