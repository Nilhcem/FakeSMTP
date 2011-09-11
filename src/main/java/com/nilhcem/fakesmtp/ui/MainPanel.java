package com.nilhcem.fakesmtp.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 3754673330087672446L;
	private static final String DEFAULT_PORT = "2525"; // 25
	private static final String DEFAULT_PATH = "./fakesmtp-mails"; // ./fakeSMTP

	// Port
	private JLabel portLabel = new JLabel("Listening port:");
	private JTextField portTextField = new JTextField();
	private StartServerButton portBtn = new StartServerButton();

	// Messages received
	private JLabel receivedLabel = new JLabel("Message(s) received:");
	private NbReceivedLabel nbReceivedLabel = new NbReceivedLabel();

	// Save incoming messages to
	private JLabel saveMessages = new JLabel("Save message(s) to: ");
	private JTextField saveMsgTextField = new JTextField(DEFAULT_PATH);

	// Tab pane
	private JTabbedPane tabbedPane = new JTabbedPane();
	private LogsPane logsPane = new LogsPane();
	private LastMailPane lastMailPane = new LastMailPane();

	// Clear logs
	private JButton clearLogs = new JButton("Clear logs");

	/* package-private */
	enum PanelActionEnum {
		START_SERVER,
		STOP_SERVER,
		CLEAR_LOGS
	}

	public MainPanel() {
		super(new MigLayout());
		portTextField.setText(MainPanel.DEFAULT_PORT);

		add(portLabel);
		add(portTextField, "w 60!"); // width 60 (min:preferred:max)
		add(portBtn, "wrap");

		add(saveMessages);
		add(saveMsgTextField, "span 2, w 200!, wrap");

		add(receivedLabel);
		add(nbReceivedLabel, "wrap");

		// Tab pane
		tabbedPane.add(logsPane, "SMTP Log");
		tabbedPane.add(lastMailPane, "Last message");
		add(tabbedPane, "span 3, w 600!, h 300!, wrap");

		// Clear logs
		clearLogs.setActionCommand(PanelActionEnum.CLEAR_LOGS.toString());
		clearLogs.addActionListener(logsPane);
		add(clearLogs, "span 3, align center");
	}

	// Returns the value in portTextField
	public String getPortTextValue() {
		return portTextField.getText();
	}

	// Returns the path value
	public String getSavePathValue() {
		return saveMsgTextField.getText();
	}

	public long getNbMsgReceived() {
		return nbReceivedLabel.getNbMsgReceived();
	}
}
