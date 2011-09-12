package com.nilhcem.fakesmtp.ui.savefolder;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class SaveMsgField extends JTextField implements MouseListener {
	private static final long serialVersionUID = -4117942890898086119L;
	private static final String DEFAULT_PATH = "./fakesmtp-mails"; // ./fakeSMTP

	public SaveMsgField() {
		super(DEFAULT_PATH);

		// Disable edition but keep the same background color
		Color bg = getBackground();
		setEditable(false);
		setBackground(bg);
		addMouseListener(this);

		// Creates the JFileChooser and set the variables first
		DirChooserHandler.INSTANCE.init(SaveMsgField.DEFAULT_PATH, this);
	}

	// Opens a folder selection dialog when clicking
	@Override
	public void mouseClicked(MouseEvent e) {
		DirChooserHandler.INSTANCE.openFolderSelection();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		DirChooserHandler.INSTANCE.openFolderSelection();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		DirChooserHandler.INSTANCE.openFolderSelection();
	}
}
