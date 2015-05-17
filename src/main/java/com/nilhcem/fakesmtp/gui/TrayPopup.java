package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.gui.listeners.AboutActionListener;
import com.nilhcem.fakesmtp.gui.listeners.ExitActionListener;
import java.awt.MenuItem;
import java.awt.PopupMenu;

/**
 * Provides the popup menu for the SystemTray icon.
 *
 * @author Vest
 * @since 2.1
 */
public class TrayPopup {

	private final I18n i18n = I18n.INSTANCE;
	private final PopupMenu popup = new PopupMenu();

	/**
	 * The popup menu used by the icon in the system tray.
	 *
	 * @param mainFrame MainFrame class.
	 */
	public TrayPopup(final MainFrame mainFrame) {
		// Create a popup menu components
		MenuItem aboutItem = new MenuItem(i18n.get("menubar.about"));
		aboutItem.addActionListener(new AboutActionListener(null));

		MenuItem exitItem = new MenuItem(i18n.get("menubar.exit"));
		exitItem.addActionListener(new ExitActionListener(mainFrame));

		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(exitItem);
	}

	/**
	 * Returns the PopupMenu object.
	 *
	 * @return the PopupMenu object.
	 */
	public PopupMenu get() {
		return popup;
	}
}
