package com.nilhcem.fakesmtp;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nilhcem.fakesmtp.ui.MainFrame;

public class FakeSMTP {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Tell the UIManager to use the platform look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		new MainFrame();
	}
}
