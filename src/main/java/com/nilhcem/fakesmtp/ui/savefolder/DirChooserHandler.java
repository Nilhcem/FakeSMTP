package com.nilhcem.fakesmtp.ui.savefolder;

import java.io.File;

import javax.swing.JFileChooser;

public enum DirChooserHandler {
	INSTANCE;

	private final JFileChooser dirChooser = new JFileChooser();
	private SaveMsgField saveField = null;

	private DirChooserHandler() {
		// Sets the JFileChooser to be only for directories.
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	// Must be called as soon as possible
	public void init(String directoryPath, SaveMsgField saveField) {
		File filePath = new File(directoryPath);
		if (filePath != null) {
			dirChooser.setCurrentDirectory(filePath);
		}

		this.saveField = saveField;
	}

	// Shows open dialog; this method does not return until the dialog is closed
	public void openFolderSelection() {
		if (saveField != null) {
			dirChooser.showOpenDialog(saveField.getParent().getParent());

			File selectedDir = dirChooser.getSelectedFile();
			if (selectedDir != null) {
				saveField.setText(selectedDir.getAbsolutePath());
			}
		}
	}
}
