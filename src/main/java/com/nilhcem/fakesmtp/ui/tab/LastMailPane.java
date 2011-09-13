package com.nilhcem.fakesmtp.ui.tab;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.server.MailListener;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public final class LastMailPane implements Observer {
	private final JScrollPane lastMailPane = new JScrollPane();
	private static final Logger LOGGER = LoggerFactory.getLogger(LastMailPane.class);
	private final JTextArea lastMailArea = new JTextArea();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	public LastMailPane() {
		lastMailArea.setEditable(false);
		lastMailPane.getViewport().add(lastMailArea, null);
		SMTPServerHandler.INSTANCE.getSMTPListener().addObserver(this);
	}

	public JScrollPane get() {
		return lastMailPane;
	}

	@Override
	public synchronized void update(Observable o, Object data) {
		if (o instanceof MailListener) {
			String mailContent = convertStreamToString((InputStream)data);
			lastMailArea.setText(mailContent);
			saveEmailToFile(mailContent);
		}
	}

	private String convertStreamToString(InputStream is) {
		final long lineNbToStartCopy = 4; // Do not copy the first 4 lines (received part)
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		long lineNb = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (++lineNb > lineNbToStartCopy) {
					sb.append(line + System.getProperty("line.separator"));
				}
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return sb.toString();
	}

	// TODO: Put this in another place.
	private void saveEmailToFile(String mailContent) {
		String filePath = String.format("%s%s%d_%s", UIModel.INSTANCE.getSavePath(), File.separator,
				UIModel.INSTANCE.getNbMessageReceived(), dateFormat.format(new Date()));
		createFileFromInputStream(filePath, mailContent);
	}

	private void createFileFromInputStream(String filePath, String mailContent) {
		// Create file
		int i = 0;
		File file = null;
		while (file == null || (file != null && file.exists())) {
			file = new File(filePath + (i++ > 0 ? Integer.toString(i) : "") + Configuration.INSTANCE.get("emails.suffix"));
		}

		// Copy String to file
		try {
			FileUtils.writeStringToFile(file, mailContent);
		} catch (IOException e) {
			// If we can't save file, we display the error in the SMTP logs
			Logger smtpLogger = LoggerFactory.getLogger(org.subethamail.smtp.server.Session.class);
			smtpLogger.error("Error: Can't save email: {}", e.getMessage());
		}
	}
}
