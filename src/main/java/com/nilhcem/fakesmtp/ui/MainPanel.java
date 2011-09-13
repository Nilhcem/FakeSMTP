package com.nilhcem.fakesmtp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import com.nilhcem.fakesmtp.ui.info.NbReceivedLabel;
import com.nilhcem.fakesmtp.ui.info.PortTextField;
import com.nilhcem.fakesmtp.ui.info.SaveMsgField;
import com.nilhcem.fakesmtp.ui.info.StartServerButton;
import com.nilhcem.fakesmtp.ui.tab.LastMailPane;
import com.nilhcem.fakesmtp.ui.tab.LogsPane;

public final class MainPanel {
	private final JPanel mainPanel = new JPanel(new MigLayout());

	// Port
	private final JLabel portLabel = new JLabel("Listening port:");
	private final PortTextField portText = new PortTextField();
	private final StartServerButton portBtn = new StartServerButton();

	// Messages received
	private final JLabel receivedLabel = new JLabel("Message(s) received:");
	private final NbReceivedLabel nbReceivedLabel = new NbReceivedLabel();

	// Save incoming messages to
	private final JLabel saveMessages = new JLabel("Save message(s) to: ");
	private final SaveMsgField saveMsgTextField = new SaveMsgField(mainPanel);

	// Tab pane
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final LogsPane logsPane = new LogsPane();
	private final LastMailPane lastMailPane = new LastMailPane();

	// Clear logs
	private final JButton clearLogs = new JButton("Clear logs");

	public MainPanel() {
		mainPanel.add(portLabel);
		mainPanel.add(portText.get(), "w 60!"); // width 60 (min:preferred:max)
		mainPanel.add(portBtn.get(), "wrap");

		mainPanel.add(saveMessages);
		mainPanel.add(saveMsgTextField.get(), "span 2, w 200!, wrap");

		mainPanel.add(receivedLabel);
		mainPanel.add(nbReceivedLabel.get(), "wrap");

		// Tab pane
		tabbedPane.add(logsPane.get(), "SMTP Log");
		tabbedPane.add(lastMailPane.get(), "Last message");
		mainPanel.add(tabbedPane, "span 3, w 600!, h 300!, wrap");

		// Clear logs
		clearLogs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logsPane.clearLogs();
			}
		});
		mainPanel.add(clearLogs, "span 3, align center");
	}

	public JPanel get() {
		return mainPanel;
	}
}
