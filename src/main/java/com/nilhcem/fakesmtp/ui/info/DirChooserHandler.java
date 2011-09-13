package com.nilhcem.fakesmtp.ui.info;

import java.awt.Component;
import java.io.File;
import java.util.Observable;

import javax.swing.JFileChooser;

import com.nilhcem.fakesmtp.ui.model.UIModel;

public final class DirChooserHandler extends Observable {
	private final JFileChooser dirChooser = new JFileChooser();
	private Component parent = null;

	private DirChooserHandler() {
		// Sets the JFileChooser to be only for directories.
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class DirChooserHandlerHolder {
		public static final DirChooserHandler INSTANCE = new DirChooserHandler();
	}

	public static DirChooserHandler getInstance() {
		return DirChooserHandlerHolder.INSTANCE;
	}

	// Must be called as soon as possible
	public void setParent(Component parent) {
		this.parent = parent;
	}

	// Shows open dialog; this method does not return until the dialog is closed
	public void openFolderSelection() {
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
