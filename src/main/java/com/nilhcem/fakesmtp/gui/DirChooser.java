package com.nilhcem.fakesmtp.gui;

import java.awt.Component;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import com.nilhcem.fakesmtp.gui.info.SaveMsgField;
import com.nilhcem.fakesmtp.model.UIModel;

public final class DirChooser extends Observable implements Observer {
	private final JFileChooser dirChooser = new JFileChooser();
	private Component parent = null;

	public DirChooser(Component parent) {
		this.parent = parent;
		// Sets the JFileChooser to be only for directories.
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MenuBar || o instanceof SaveMsgField) {
			openFolderSelection();
		}
	}

	// Shows open dialog; this method does not return until the dialog is closed
	private void openFolderSelection() {
		File filePath = new File(UIModel.INSTANCE.getSavePath());
		if (filePath != null) {
			dirChooser.setCurrentDirectory(filePath);
		}

		int result = dirChooser.showOpenDialog(parent);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedDir = dirChooser.getSelectedFile();
			if (selectedDir != null) {
				UIModel.INSTANCE.setSavePath(selectedDir.getAbsolutePath());
				setChanged();
				notifyObservers();
			}
		}
	}
}
