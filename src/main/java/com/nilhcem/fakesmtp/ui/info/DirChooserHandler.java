package com.nilhcem.fakesmtp.ui.info;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import com.nilhcem.fakesmtp.log.ILogObservable;
import com.nilhcem.fakesmtp.log.ILogObserver;

public enum DirChooserHandler implements ILogObservable {
	INSTANCE;

	private final ArrayList<ILogObserver> observers = new ArrayList<ILogObserver>();
	private final JFileChooser dirChooser = new JFileChooser();
	Component parent = null;

	private DirChooserHandler() {
		// Sets the JFileChooser to be only for directories.
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	// Must be called as soon as possible
	public void init(Component parent) {
		this.parent = parent;
		File filePath = new File(UIModel.INSTANCE.getSavePath());
		if (filePath != null) {
			dirChooser.setCurrentDirectory(filePath);
		}
	}

	// Shows open dialog; this method does not return until the dialog is closed
	public void openFolderSelection() {
		dirChooser.showOpenDialog(parent);

		File selectedDir = dirChooser.getSelectedFile();
		if (selectedDir != null) {
			UIModel.INSTANCE.setSavePath(selectedDir.getAbsolutePath());
			notifyObservers(null);
		}
	}

	@Override
	public void addObserver(ILogObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(ILogObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String useless) {
		for (ILogObserver observer : observers) {
			observer.update(null);
		}
	}
}
