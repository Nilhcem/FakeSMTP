package com.nilhcem.fakesmtp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.nilhcem.fakesmtp.ui.savefolder.DirChooserHandler;

public final class MenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = -9155013983398541594L;

	private enum MenuActionEnum {
		ABOUT,
		CHANGE_MAIL_DIR,
		EXIT
	}

	public MenuBar() {
		createFileMenu();
		createEditMenu();
		createHelpMenu();
	}

	// creates and adds the file menu
	private void createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");

		exit.setActionCommand(MenuActionEnum.EXIT.toString());
		exit.addActionListener(this);

		fileMenu.add(exit);
		add(fileMenu);
	}

	// creates and adds the edit menu
	private void createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		JMenuItem messagesLocation = new JMenuItem("Messages location");

		messagesLocation.setActionCommand(MenuActionEnum.CHANGE_MAIL_DIR.toString());
		messagesLocation.addActionListener(this);

		editMenu.add(messagesLocation);
		add(editMenu);
	}

	// creates and adds the help menu
	private void createHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand(MenuActionEnum.ABOUT.toString());
		about.addActionListener(this);

		helpMenu.add(about);
		add(helpMenu);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();

		if (actionCommand.equals(MenuActionEnum.EXIT.toString())) {
			System.exit(0);
		} else if (actionCommand.equals(MenuActionEnum.ABOUT.toString())) {
			JOptionPane.showMessageDialog(getParent(), String.format("Fake SMTP Server v1.0%nPowered by SubEtha SMTP."), "About", JOptionPane.INFORMATION_MESSAGE);
		} else if (actionCommand.equals(MenuActionEnum.CHANGE_MAIL_DIR.toString())) {
			DirChooserHandler.INSTANCE.openFolderSelection();
		}
	}
}
