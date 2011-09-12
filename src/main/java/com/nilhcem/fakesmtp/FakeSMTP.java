package com.nilhcem.fakesmtp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nilhcem.fakesmtp.ui.MainFrame;

public final class FakeSMTP {
	private FakeSMTP() {
	}

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Apple OS X fix
		System.setProperty("apple.laf.useScreenMenuBar", "true"); // take the menu bar off the JFrame
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Fake SMTP"); // set the name of the application menu item

		// Tell the UIManager to use the platform look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		new MainFrame();
	}
}
