package com.nilhcem.fakesmtp;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.UIManager;

import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apple.eawt.Application;
import com.nilhcem.fakesmtp.core.ArgsHandler;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.UncaughtExceptionHandler;
import com.nilhcem.fakesmtp.gui.MainFrame;

/**
 * Entry point of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class FakeSMTP {
	private static final Logger LOGGER = LoggerFactory.getLogger(FakeSMTP.class);

	private FakeSMTP() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks command line arguments, sets some specific properties, and runs the main window.
	 * <p>
	 * Before opening the main window, this method will:
	 * <ul>
	 *   <li>check command line arguments, and possibly display an error dialog,</li>
	 *   <li>set a default uncaught exception handler to intercept every uncaught exception;</li>
	 *   <li>use a custom icon in the Mac Dock;</li>
	 *   <li>set a property for Mac OS X to take the menu bar off the JFrame;</li>
	 *   <li>set a property for Mac OS X to set the name of the application menu item;</li>
	 *   <li>turn off the bold font in all components for swing default theme;</li>
	 *   <li>use the platform look and feel.</li>
	 * </ul>
	 * </p>
	 *
	 * @param args a list of command line parameters.
	 */
	public static void main(final String[] args) {
		try {
			ArgsHandler.INSTANCE.handleArgs(args);
		} catch (ParseException e) {
			ArgsHandler.INSTANCE.displayUsage(e);
			return;
		}

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					URL envelopeImage = getClass().getResource(Configuration.INSTANCE.get("application.icon.path"));
					if (envelopeImage != null) {
						Application.getApplication().setDockIconImage(Toolkit.getDefaultToolkit().getImage(envelopeImage));
					}
				} catch (RuntimeException e) {
					LOGGER.debug("Error: {} - This is probably because we run on a non-Mac platform and these components are not implemented", e.getMessage());
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
