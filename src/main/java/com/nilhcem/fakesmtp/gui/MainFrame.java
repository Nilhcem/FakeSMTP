package com.nilhcem.fakesmtp.gui;

import javax.swing.JFrame;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public final class MainFrame {
	private final JFrame mainFrame = new JFrame(Configuration.INSTANCE.get("application.title"));
	private final MenuBar menu = new MenuBar();
	private final MainPanel panel = new MainPanel(menu);

	public MainFrame() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(Integer.parseInt(Configuration.INSTANCE.get("application.min.width")),
			Integer.parseInt(Configuration.INSTANCE.get("application.min.height")));

		mainFrame.setJMenuBar(menu.get());
		mainFrame.getContentPane().add(panel.get());
		mainFrame.setLocationRelativeTo(null); // Center main frame

		// Add shutdown hook to stop server if enabled
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				SMTPServerHandler.INSTANCE.stopServer();
			};
		});

		mainFrame.setVisible(true);
	}
}
