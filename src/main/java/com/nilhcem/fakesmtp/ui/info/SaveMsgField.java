package com.nilhcem.fakesmtp.ui.info;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;
import com.nilhcem.fakesmtp.log.ILogObserver;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public final class SaveMsgField implements ILogObserver {
	private final JTextField saveMsgField;

	public SaveMsgField(Component parent) {
		this.saveMsgField = new JTextField(UIModel.INSTANCE.getSavePath());

		// Disable edition but keep the same background color
		Color bg = saveMsgField.getBackground();
		saveMsgField.setEditable(false);
		saveMsgField.setBackground(bg);

		// Creates the JFileChooser and set the variables first
		DirChooserHandler.INSTANCE.init(parent);
		DirChooserHandler.INSTANCE.addObserver(this);

		// AddMouseListener
		saveMsgField.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openFolderSelection();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				openFolderSelection();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				openFolderSelection();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	public JTextField get() {
		return saveMsgField;
	}

	private void openFolderSelection() {
		DirChooserHandler.INSTANCE.openFolderSelection();
	}

	@Override
	public void update(String log) {
		saveMsgField.setText(UIModel.INSTANCE.getSavePath());
	}
}
