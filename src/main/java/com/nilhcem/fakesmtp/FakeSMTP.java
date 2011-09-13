package com.nilhcem.fakesmtp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.ui.MainFrame;

public final class FakeSMTP {
	private FakeSMTP() {
	}

	// Sets some specific properties to the application and runs the main window.
	public static void main(String[] args) throws ClassNotFoundException,
		InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Apple OS X fix: take the menu bar off the JFrame
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		// Apple OS X fix: set the name of the application menu item
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Configuration.INSTANCE.get("application.name"));

		// Tell the UIManager to use the platform look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		new MainFrame();
	}
}
