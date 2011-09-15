package com.nilhcem.fakesmtp.gui.info;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextField;
import com.nilhcem.fakesmtp.gui.DirChooser;
import com.nilhcem.fakesmtp.model.UIModel;

public final class SaveMsgField extends Observable implements Observer {
	private final JTextField saveMsgField;

	public SaveMsgField() {
		this.saveMsgField = new JTextField(UIModel.INSTANCE.getSavePath());

		// Disable edition but keep the same background color
		Color bg = saveMsgField.getBackground();
		saveMsgField.setEditable(false);
		saveMsgField.setBackground(bg);

		// AddMouseListener
		saveMsgField.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				openFolderSelection();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			private void openFolderSelection() {
				setChanged();
				notifyObservers();
			}
		});
	}

	public JTextField get() {
		return saveMsgField;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof DirChooser) {
			saveMsgField.setText(UIModel.INSTANCE.getSavePath());
		}
	}
}
