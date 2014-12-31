package com.nilhcem.fakesmtp.gui.info;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.MailSaver;

import com.apple.eawt.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Label class to display the number of received emails.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class NbReceivedLabel implements Observer {

	private static final Logger LOGGER = LoggerFactory.getLogger(NbReceivedLabel.class);

	private final JLabel nbReceived = new JLabel("0");

	/**
	 * Creates the label class (with a bold font).
	 */
	public NbReceivedLabel() {
		Font boldFont = new Font(nbReceived.getFont().getName(), Font.BOLD, nbReceived.getFont().getSize());
		nbReceived.setFont(boldFont);
	}

	/**
	 * Returns the JLabel object.
	 *
	 * @return the JLabel object.
	 */
	public JLabel get() {
		return nbReceived;
	}

	/**
	 * Actions which will be done when the component will be notified by an Observable object.
	 * <ul>
	 *   <li>If the observable element is a {@link MailSaver} object, the method will increment
	 *   the number of received messages and update the {@link UIModel};</li>
	 *   <li>If the observable element is a {@link ClearAllButton}, the method will reinitialize
	 *   the number of received messages and update the {@link UIModel};</li>
	 *   <li>When running on OS X the method will also update the Dock Icon with the number of
	 *   received messages.</li>
	 * </ul>
	 *
	 * @param o the observable element which will notify this class.
	 * @param arg optional parameters (not used).
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MailSaver) {
			UIModel model = UIModel.INSTANCE;
			int countMsg = model.getNbMessageReceived() + 1;
			String countMsgStr = Integer.toString(countMsg);

			model.setNbMessageReceived(countMsg);
			updateDockIconBadge(countMsgStr);
			nbReceived.setText(countMsgStr);
		} else if (o instanceof ClearAllButton) {
			UIModel.INSTANCE.setNbMessageReceived(0);
			updateDockIconBadge("");
			nbReceived.setText("0");
		}
	}

	private void updateDockIconBadge(String badgeValue) {
		try {
			Application.getApplication().setDockIconBadge(badgeValue);
		} catch (RuntimeException e) {
			LOGGER.debug("Error: {} - This is probably because we run on a non-Mac platform and these components are not implemented", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
}
