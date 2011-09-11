package com.nilhcem.fakesmtp.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.mail.IMailObserver;
import com.nilhcem.fakesmtp.mail.SMTPServerHandler;

public class LastMailPane extends JScrollPane implements IMailObserver {
	private static final long serialVersionUID = 7824377979564256075L;
	private final JTextArea lastMailArea = new JTextArea();
	private static final String MAIL_SUFFIX = ".eml";
	private static final Logger logger = LoggerFactory.getLogger(LastMailPane.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	public LastMailPane() {
		super();
		getViewport().add(lastMailArea, null);
		SMTPServerHandler.INSTANCE.getSMTPListener().addObserver(this);
	}

	@Override
	public synchronized void update(InputStream data) {
		String mailContent = convertStreamToString(data);
		lastMailArea.setText(mailContent);
		saveEmail(mailContent);
	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		long lineNb = 0;
		try {
			while ((line = reader.readLine()) != null) {
				// Do not copy the first 3 lines (received part).
				if (++lineNb > 4) {
					sb.append(line + System.getProperty("line.separator"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void saveEmail(String mailContent) {
		MainPanel mainPanel = (MainPanel)getParent().getParent();

		String filePath = String.format("%s%s%d_%s", mainPanel.getSavePathValue(), File.separator,
				mainPanel.getNbMsgReceived(), dateFormat.format(new Date()));
		createFileFromInputStream(filePath, mailContent);
	}

	private void createFileFromInputStream(String filePath, String mailContent) {
		// Create file
		int i = 0;
		File file = null;
		while (file == null || (file != null && file.exists())) {
			file = new File(filePath + (i++ > 0 ? Integer.toString(i) : "") + LastMailPane.MAIL_SUFFIX);
		}

		// Copy String to file
		try {
			FileUtils.writeStringToFile(file, mailContent);
		} catch (IOException e) {
			logger.error("", e);
		}
	}
}
