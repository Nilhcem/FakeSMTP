package com.nilhcem.fakesmtp.ui.tab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.AppenderAttachable;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.log.SMTPLogsAppender;
import com.nilhcem.fakesmtp.log.SMTPLogsObservable;

public final class LogsPane implements Observer {
	private final JScrollPane logsPane = new JScrollPane();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
	private final JTextArea logsArea = new JTextArea();

	public LogsPane() {
		logsArea.setEditable(false);
		logsPane.getViewport().add(logsArea, null);
		addObserverToSmtpLogAppender();
	}

	public JScrollPane get() {
		return logsPane;
	}

	// Adds this observer to the SMTP log appender
	// The goal is to be informed when the log appender will received some debug SMTP logs.
	private void addObserverToSmtpLogAppender() {
		Logger smtpLogger = LoggerFactory.getLogger(org.subethamail.smtp.server.Session.class);
		String appenderName = Configuration.INSTANCE.get("logback.appender.name");

		@SuppressWarnings("unchecked")
		SMTPLogsAppender<ILoggingEvent> appender = (SMTPLogsAppender<ILoggingEvent>)((AppenderAttachable<ILoggingEvent>)smtpLogger)
			.getAppender(appenderName);
		if (appender == null) {
			LoggerFactory.getLogger(LogsPane.class).error("Can't find logger: {}", appenderName);
		}
		else {
			appender.getObservable().addObserver(this);
		}
	}

	// Updates and scroll pane to the bottom
	@Override
	public void update(Observable o, Object log) {
		if (o instanceof SMTPLogsObservable) {
			logsArea.append(String.format("%s - %s%n", dateFormat.format(new Date()), log));
			logsArea.setCaretPosition(logsArea.getText().length());
		}
	}

	public void clearLogs() {
		logsArea.setText("");
	}
}
