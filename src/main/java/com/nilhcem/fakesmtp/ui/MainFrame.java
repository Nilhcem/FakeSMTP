package com.nilhcem.fakesmtp.ui;

import javax.swing.JFrame;

import com.nilhcem.fakesmtp.mail.SMTPServerHandler;

public final class MainFrame extends JFrame {
	private static final long serialVersionUID = 54795562728273075L;
	private static final int HEIGHT = 470;
	private static final int WIDTH = 620;

	private MenuBar menu = new MenuBar();
	private MainPanel panel = new MainPanel();

	public MainFrame() {
		super("Fake SMTP Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);

		setJMenuBar(menu);
		getContentPane().add(panel);
		setLocationRelativeTo(getRootPane()); // center component

		// Add shutdown hook to stop server if enabled
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				SMTPServerHandler.INSTANCE.stopServer();
			};
		});

		setVisible(true);
	}
}
