package com.nilhcem.fakesmtp;

import java.awt.EventQueue;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.UncaughtExceptionHandler;
import com.nilhcem.fakesmtp.gui.MainFrame;
import com.apple.eawt.Application;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Entry point of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class FakeSMTP {
	private static final Logger LOGGER = LoggerFactory.getLogger(FakeSMTP.class);

	private FakeSMTP() {
	}

	/**
	 * Sets some specific properties, and runs the main window.
	 * <p>
	 * Before opening the main window, this method will:
	 * <ul>
	 *   <li>set a default uncaught exception handler to intercept every uncaught exception;</li>
	 *   <li>set a property for Mac OS X to take the menu bar off the JFrame;</li>
	 *   <li>set a property for Mac OS X to set the name of the application menu item;</li>
	 *   <li>turn off the bold font in all components for swing default theme;</li>
	 *   <li>use the platform look and feel.</li>
	 * </ul>
	 * </p>
	 *
	 * @param args a list of parameters.
	 */
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Application.getApplication().setDockIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Configuration.INSTANCE.get("application.icon.path"))));
				} catch (Exception e) {
					LOGGER.error("", e);
				}

				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", Configuration.INSTANCE.get("application.name"));
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					LOGGER.error("", e);
				}
				new MainFrame();
			}
		});
	}
}
