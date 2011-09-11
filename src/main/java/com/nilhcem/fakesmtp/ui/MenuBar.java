package com.nilhcem.fakesmtp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 3023456490990170945L;
	private JMenu file;
	private JMenu help;

	private enum MenuActionEnum {
		ABOUT,
		EXIT
	}

	public MenuBar() {
		addFileMenu();
		addHelpMenu();
	}

	// creates and adds the file menu
	private void addFileMenu() {
		file = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");

		exit.setActionCommand(MenuActionEnum.EXIT.toString());
		exit.addActionListener(this);
		file.add(exit);
		add(file);
	}

	// creates and adds the help menu
	private void addHelpMenu() {
		help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand(MenuActionEnum.ABOUT.toString());
		about.addActionListener(this);

		help.add(about);
		add(help);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();

		if (actionCommand.equals(MenuActionEnum.EXIT.toString())) {
			System.exit(0);
		} else if (actionCommand.equals(MenuActionEnum.ABOUT.toString())) {
			JOptionPane.showMessageDialog(getParent(), String.format("Fake SMTP Server v1.0%nSeptember 2011."), "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
