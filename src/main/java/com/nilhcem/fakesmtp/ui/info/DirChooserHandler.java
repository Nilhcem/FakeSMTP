package com.nilhcem.fakesmtp.ui.info;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import com.nilhcem.fakesmtp.log.ILogObservable;
import com.nilhcem.fakesmtp.log.ILogObserver;
import com.nilhcem.fakesmtp.ui.model.UIModel;

public enum DirChooserHandler implements ILogObservable {
	INSTANCE;

	private final List<ILogObserver> observers = new ArrayList<ILogObserver>();
	private final JFileChooser dirChooser = new JFileChooser();
	private Component parent = null;

	private DirChooserHandler() {
		// Sets the JFileChooser to be only for directories.
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	// Must be called as soon as possible
	public void init(Component parent) {
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
				notifyObservers(null);
			}
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
