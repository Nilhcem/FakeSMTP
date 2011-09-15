package com.nilhcem.fakesmtp.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.UIModel;

public final class MailSaver extends Observable {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSaver.class);
	private final Pattern subjectPattern = Pattern.compile("^Subject: (.*)$");
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	// saves email and notify observers
	public void saveEmailAndNotify(String from, String to, InputStream data) {
		synchronized (getLock()) {
			String mailContent = convertStreamToString(data);
			String filePath = saveEmailToFile(mailContent);

			EmailModel model = new EmailModel();
			model.setReceivedDate(new Date());
			model.setFrom(from);
			model.setTo(to);
			model.setSubject(getSubjectFromStr(mailContent));
			model.setEmailStr(mailContent);
			model.setFilePath(filePath);

			setChanged();
			notifyObservers(model);
		}
	}

	// Deletes all emails
	public void deleteEmails() {
		Map<Integer, String> mails = UIModel.INSTANCE.getListMailsMap();

		for (String value : mails.values()) {
			File file = new File(value);
			if (file.exists()) {
				try {
					if (!file.delete()) {
						LOGGER.error("Impossible to delete file {}", value);
					}
				} catch (SecurityException e) {
					LOGGER.error("", e);
				}
			}
		}
	}

	public Object getLock() {
		return this;
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

	private String saveEmailToFile(String mailContent) {
		String filePath = String.format("%s%s%s", UIModel.INSTANCE.getSavePath(), File.separator,
				dateFormat.format(new Date()));
		return createFileFromInputStream(filePath, mailContent);
	}

	private String createFileFromInputStream(String filePath, String mailContent) {
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
		return file.getAbsolutePath();
	}

	// Returns an empty subject if not found
	private String getSubjectFromStr(String data) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(data));

			String line = null;
			while ((line = reader.readLine()) != null) {
				 Matcher matcher = subjectPattern.matcher(line);
				 if (matcher.matches()) {
					 return matcher.group(1);
				 }
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return "";
	}
}
