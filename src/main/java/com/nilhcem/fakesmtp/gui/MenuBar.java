package com.nilhcem.fakesmtp.gui;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Observable;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.I18n;

/**
 * Provides the menu bar of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MenuBar extends Observable {
	private final I18n i18n = I18n.INSTANCE;
	private final JMenuBar menuBar = new JMenuBar();

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuBar.class);

	/**
	 * Creates the menu bar and the different menus (file / edit / help).
	 */
	public MenuBar() {
		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createHelpMenu());
	}

	/**
	 * Returns the JMenuBar object.
	 *
	 * @return the JMenuBar object.
	 */
	public JMenuBar get() {
		return menuBar;
	}

	/**
	 * Creates the file menu.
	 * <p>
	 * The file menu contains an "Exit" item, to quit the application.
	 * </p>
	 *
	 * @return the newly created file menu.
	 */
	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu(i18n.get("menubar.file"));
		fileMenu.setMnemonic(i18n.get("menubar.mnemo.file").charAt(0));

		JMenuItem exit = new JMenuItem(i18n.get("menubar.exit"));
		exit.setMnemonic(i18n.get("menubar.mnemo.exit").charAt(0));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		fileMenu.add(exit);
		return fileMenu;
	}

	/**
	 * Creates the edit menu.
	 * <p>
	 * The edit menu contains a "Messages location" item, to define the location of the incoming mails.
	 * </p>
	 *
	 * @return the newly created edit menu.
	 */
	private JMenu createEditMenu() {
		JMenu editMenu = new JMenu(i18n.get("menubar.edit"));
		editMenu.setMnemonic(i18n.get("menubar.mnemo.edit").charAt(0));

		JMenuItem mailsLocation = new JMenuItem(i18n.get("menubar.messages.location"));
		mailsLocation.setMnemonic(i18n.get("menubar.mnemo.msglocation").charAt(0));
		mailsLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setChanged();
				notifyObservers();
			}
		});

		editMenu.add(mailsLocation);
		return editMenu;
	}

	/**
	 * Creates the help menu.
	 * <p>
	 * The help menu contains an "About" item, to display some software information.
	 * </p>
	 *
	 * @return the newly created help menu.
	 */
	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu(i18n.get("menubar.help"));
		helpMenu.setMnemonic(i18n.get("menubar.mnemo.help").charAt(0));

		JMenuItem about = new JMenuItem(i18n.get("menubar.about"));
		about.setMnemonic(i18n.get("menubar.mnemo.about").charAt(0));
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// for copying style
				JLabel label = new JLabel();
				Font font = label.getFont();

				// create some css from the label's font
				StringBuffer style = new StringBuffer("font-family:")
					.append(font.getFamily()).append(";font-weight:");
				if (font.isBold()) {
					style.append("bold");
				} else {
					style.append("normal");
				}
				style.append(";font-size:").append(font.getSize()).append("pt;");

				// html content
				String link = i18n.get("menubar.about.dialog.link");
				JEditorPane ep = new JEditorPane("text/html",
						String.format("<html><body style=\"%s\">%s<br /><a href=\"%s\">%s</a></body></html>",
								style, i18n.get("menubar.about.dialog"), link, link));

				// handle link events
				ep.addHyperlinkListener(new HyperlinkListener() {
					@Override
					public void hyperlinkUpdate(HyperlinkEvent e) {
						if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
							MenuBar.launchUrl(e.getURL().toString());
						}
					}
				});
				ep.setEditable(false);
				ep.setBackground(label.getBackground());

				// show
				JOptionPane.showMessageDialog(menuBar.getParent(), ep, String.format(i18n.get("menubar.about.title"),
						Configuration.INSTANCE.get("application.name")), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		helpMenu.add(about);
		return helpMenu;
	}

	/**
	 * Opens a web browser to launch the url specified in parameters.
	 *
	 * @param url the URL to launch.
	 */
	private static void launchUrl(String url) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop desktop = Desktop.getDesktop();
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI(url));
				}
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
	}
}
