package com.nilhcem.fakesmtp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.gui.MainFrame;

/**
 * Entry point of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class FakeSMTP {
	private FakeSMTP() {
	}

	/**
	 * Sets some specific properties, and runs the main window.
	 * <p>
	 * Before opening the main window, this method will:
	 * <ul>
	 *   <li>set a property for Mac OS X to take the menu bar off the JFrame;</li>
	 *   <li>set a property for Mac OS X to set the name of the application menu item;</li>
	 *   <li>turn off the bold font in all components for swing default theme;</li>
	 *   <li>use the platform look and feel.</li>
	 * </ul>
	 * </p>
	 *
	 * @param args a list of parameters.
	 * @throws ClassNotFoundException if an error happened while setting the system look and feel.
	 * @throws InstantiationException if an error happened while setting the system look and feel.
	 * @throws IllegalAccessException if an error happened while setting the system look and feel.
	 * @throws UnsupportedLookAndFeelException if an error happened while setting the system look and feel.
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
		IllegalAccessException, UnsupportedLookAndFeelException {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Configuration.INSTANCE.get("application.name"));
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		new MainFrame();
	}
}
