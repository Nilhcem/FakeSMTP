package com.nilhcem.fakesmtp.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

/**
 * Provides the main window of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MainFrame {
	private final JFrame mainFrame = new JFrame(Configuration.INSTANCE.get("application.title"));
	private final MenuBar menu = new MenuBar();
	private final MainPanel panel = new MainPanel(menu);

	/**
	 * Creates the main window and make it visible.
	 * <p>
	 * To create the main window, the application will have to set some elements, such as:
	 * <ul>
	 *   <li>The minimum and default size;</li>
	 *   <li>The menu bar and the main panel;</li>
	 *   <li>An icon image;</li>
	 *   <li>A shutdown hook to stop the server, once the main window is closed.</li>
	 * </ul><br />
	 * The icon of the application is a modified version from the one provided in "{@code WebAppers.com}"
	 * <i>(Creative Commons Attribution 3.0 License)</i>.
	 * </p>
	 */
	public MainFrame() {
		Dimension frameSize = new Dimension(Integer.parseInt(Configuration.INSTANCE.get("application.min.width")),
			Integer.parseInt(Configuration.INSTANCE.get("application.min.height")));

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(frameSize);
		mainFrame.setMinimumSize(frameSize);

		mainFrame.setJMenuBar(menu.get());
		mainFrame.getContentPane().add(panel.get());
		mainFrame.setLocationRelativeTo(null); // Center main frame
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
			getResource(Configuration.INSTANCE.get("application.icon.path"))));

		// Add shutdown hook to stop server if enabled
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				SMTPServerHandler.INSTANCE.stopServer();
			};
		});

		mainFrame.setVisible(true);
	}
}
