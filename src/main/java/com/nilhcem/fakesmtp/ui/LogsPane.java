package com.nilhcem.fakesmtp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.AppenderAttachable;

import com.nilhcem.fakesmtp.log.ILogObserver;
import com.nilhcem.fakesmtp.log.SMTPLogsAppender;
import com.nilhcem.fakesmtp.ui.MainPanel.PanelActionEnum;

// Scrollable pane containing a text area for logs
// Observer always updated
public final class LogsPane extends JScrollPane implements ActionListener, ILogObserver {
	private static final long serialVersionUID = -3886655723914600051L;
	private static final Logger LOGGER = LoggerFactory.getLogger(LogsPane.class);
	private static final String SMTP_LOGS_APPENDER_NAME = "SMTPLOGS";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
	private final JTextArea logsArea = new JTextArea();

	public LogsPane() {
		super();
		getViewport().add(logsArea, null);
		addObserverToSmtpLogAppender();
	}

	// Adds this observer to the smtp log appender
	// The goal is to be informed when the log appender will received some debug SMTP logs.
	private void addObserverToSmtpLogAppender() {
		Logger smtpLogger = LoggerFactory.getLogger(org.subethamail.smtp.server.Session.class);
		@SuppressWarnings("unchecked")
		SMTPLogsAppender<ILoggingEvent> appender = (SMTPLogsAppender<ILoggingEvent>)((AppenderAttachable<ILoggingEvent>) smtpLogger)
			.getAppender(LogsPane.SMTP_LOGS_APPENDER_NAME);
		if (appender == null) {
			LOGGER.error("Can't find logger: {}", LogsPane.SMTP_LOGS_APPENDER_NAME);
		} else {
			appender.addObserver(this);
		}
	}

	@Override
	public void update(String log) {
		logsArea.append(String.format("%s - %s%n", dateFormat.format(new Date()), log));
	}

	private void clearLogs() {
		logsArea.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();

		if (actionCommand.equals(PanelActionEnum.CLEAR_LOGS.toString())) {
			clearLogs();
		}
	}
}
