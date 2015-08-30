package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.ArgsHandler;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.UncaughtExceptionHandler;
import com.nilhcem.fakesmtp.gui.listeners.MainWindowListener;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import org.slf4j.Logger;

/**
 * Provides the main window of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MainFrame {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);

	private final JFrame mainFrame = new JFrame(Configuration.INSTANCE.get("application.title"));
	private final MenuBar menu = new MenuBar(this);
	private final MainPanel panel = new MainPanel(menu);

	/**
	 * Creates the main window and makes it visible.
	 * <p>
	 * First, assigns the main panel to the default uncaught exception handler to display exceptions in this panel.<br><br>
	 * Before creating the main window, the application will have to set some elements, such as:
	 * </p>
	 * <ul>
	 *   <li>The minimum and default size;</li>
	 *   <li>The menu bar and the main panel;</li>
	 *   <li>An icon image;</li>
	 *   <li>A shutdown hook to stop the server, once the main window is closed.</li>
	 * </ul><br>
	 * <p>
	 * The icon of the application is a modified version from the one provided in "{@code WebAppers.com}"
	 * <i>(Creative Commons Attribution 3.0 License)</i>.
	 * </p>
	 */
	public MainFrame() {
		((UncaughtExceptionHandler) Thread.getDefaultUncaughtExceptionHandler()).setParentComponent(panel.get());
		Dimension frameSize = new Dimension(Integer.parseInt(Configuration.INSTANCE.get("application.min.width")),
			Integer.parseInt(Configuration.INSTANCE.get("application.min.height")));

		Image iconImage = Toolkit.getDefaultToolkit().getImage(
			getClass().getResource(Configuration.INSTANCE.get("application.icon.path")));

		MainWindowListener windowListener = new MainWindowListener(this);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});

		mainFrame.addWindowStateListener(windowListener); // used for TrayIcon
		mainFrame.setSize(frameSize);
		mainFrame.setMinimumSize(frameSize);

		mainFrame.setJMenuBar(menu.get());
		mainFrame.getContentPane().add(panel.get());
		mainFrame.setLocationRelativeTo(null); // Center main frame
		mainFrame.setIconImage(iconImage);

		// Add shutdown hook to stop server if enabled
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				SMTPServerHandler.INSTANCE.stopServer();
			}
		});

		// Restore last saved smtp port (if not overridden by the user)
		String smtpPort = ArgsHandler.INSTANCE.getPort();
		if (smtpPort == null) {
			smtpPort = Configuration.INSTANCE.get("smtp.default.port");
		}
		panel.getPortText().setText(smtpPort);

		// Restore last emails directory (if not overridden by the user)
		String emailsDir = ArgsHandler.INSTANCE.getOutputDirectory();
		if (emailsDir == null) {
			emailsDir = Configuration.INSTANCE.get("emails.default.dir");
		}
		if (emailsDir != null && !emailsDir.isEmpty()) {
			panel.getSaveMsgTextField().get().setText(emailsDir);
			UIModel.INSTANCE.setSavePath(emailsDir);
		}

		mainFrame.setVisible(true);
	}

	public void close() {
		LOGGER.debug("Closing the application and saving the configuration");

		Configuration.INSTANCE.set("smtp.default.port", panel.getPortText().get().getText());
		Configuration.INSTANCE.set("emails.default.dir", panel.getSaveMsgTextField().get().getText());

		try {
			Configuration.INSTANCE.saveToUserProfile();
		} catch (IOException ex) {
			LOGGER.error("Could not save configuration", ex);
		}
		// Check for SMTP server running and stop it
		if (SMTPServerHandler.INSTANCE.getSmtpServer().isRunning()) {
			SMTPServerHandler.INSTANCE.getSmtpServer().stop();
		}

		mainFrame.dispose();
	}
}
