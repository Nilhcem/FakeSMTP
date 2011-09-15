package com.nilhcem.fakesmtp.gui;

import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import com.nilhcem.fakesmtp.gui.info.ClearAllButton;
import com.nilhcem.fakesmtp.gui.info.NbReceivedLabel;
import com.nilhcem.fakesmtp.gui.info.PortTextField;
import com.nilhcem.fakesmtp.gui.info.SaveMsgField;
import com.nilhcem.fakesmtp.gui.info.StartServerButton;
import com.nilhcem.fakesmtp.gui.tab.LastMailPane;
import com.nilhcem.fakesmtp.gui.tab.LogsPane;
import com.nilhcem.fakesmtp.gui.tab.MailsListPane;
import com.nilhcem.fakesmtp.server.MailSaver;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public final class MainPanel {
	// Panel and layout
	private final MigLayout layout = new MigLayout(
		"", // Layout constraints
		"[] 10 [] [] [grow,fill]", // Column constraints
		"[] [] 5 [] 5 [grow,fill] []"); // Row constraints
	private final JPanel mainPanel = new JPanel(layout);

	// Directory chooser
	private final DirChooser dirChooser = new DirChooser(mainPanel);

	// Port
	private final JLabel portLabel = new JLabel("Listening port:");
	private final PortTextField portText = new PortTextField();
	private final StartServerButton startServerBtn = new StartServerButton();

	// Messages received
	private final JLabel receivedLabel = new JLabel("Message(s) received:");
	private final NbReceivedLabel nbReceivedLabel = new NbReceivedLabel();

	// Save incoming messages to
	private final JLabel saveMessages = new JLabel("Save message(s) to: ");
	private final SaveMsgField saveMsgTextField = new SaveMsgField();

	// Tab pane
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final LogsPane logsPane = new LogsPane();
	private final MailsListPane mailsListPane = new MailsListPane();
	private final LastMailPane lastMailPane = new LastMailPane();

	// Clear all
	private final ClearAllButton clearAll = new ClearAllButton();

	public MainPanel(Observable menu) {
		addObservers(menu);
		buildGUI();
	}

	public JPanel get() {
		return mainPanel;
	}

	// Centralize all observers
	private void addObservers(Observable menu) {
		// When we want to select a directory
		menu.addObserver(dirChooser);
		saveMsgTextField.addObserver(dirChooser);

		// When we click on "start/stop server" button
		startServerBtn.addObserver(portText);

		// Once we chose a directory
		dirChooser.addObserver(saveMsgTextField);

		// When a message is received
		MailSaver mailSaver = SMTPServerHandler.INSTANCE.getMailSaver();
		mailSaver.addObserver(nbReceivedLabel);
		mailSaver.addObserver(mailsListPane);
		mailSaver.addObserver(lastMailPane);
		mailSaver.addObserver(clearAll);

		// When we click on "clear all"
		clearAll.addObserver(nbReceivedLabel);
		clearAll.addObserver(mailsListPane);
		clearAll.addObserver(logsPane);
		clearAll.addObserver(lastMailPane);
	}

	private void buildGUI() {
		// Port / Start server
		mainPanel.add(portLabel);
		mainPanel.add(portText.get(), "w 60!"); // width 60 (min:preferred:max)
		mainPanel.add(startServerBtn.get(), "span, w 165!");

		// Save messages to...
		mainPanel.add(saveMessages);
		mainPanel.add(saveMsgTextField.get(), "span, w 230!");

		// Nb received
		mainPanel.add(receivedLabel);
		mainPanel.add(nbReceivedLabel.get(), "span");

		// Tab pane
		tabbedPane.add(mailsListPane.get(), "Mails list");
		tabbedPane.add(logsPane.get(), "SMTP log");
		tabbedPane.add(lastMailPane.get(), "Last message");
		mainPanel.add(tabbedPane, "span, grow");

		// Clear all
		mainPanel.add(clearAll.get(), "span, center");
	}
}
