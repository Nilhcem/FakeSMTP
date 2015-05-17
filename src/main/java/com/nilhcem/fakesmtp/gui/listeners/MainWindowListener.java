package com.nilhcem.fakesmtp.gui.listeners;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.gui.MainFrame;
import com.nilhcem.fakesmtp.gui.TrayPopup;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for window minimizing and closing. If SystemTray is supported,
 * the window MainFrame is minimized to an icon.
 *
 * @author Vest
 * @since 2.1
 */
public class MainWindowListener extends WindowAdapter {

	private final MainFrame mainFrame;
	private TrayIcon trayIcon = null;
	private final boolean useTray;

	private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowListener.class);

	/**
	 * @param mainFrame The MainFrame class used for closing actions.
	 */
	public MainWindowListener(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		useTray = (SystemTray.isSupported() && Boolean.parseBoolean(Configuration.INSTANCE.get("application.tray.use")));

		if (useTray) {
			final TrayPopup trayPopup = new TrayPopup(mainFrame);

			final Image iconImage = Toolkit.getDefaultToolkit().getImage(getClass().
				getResource(Configuration.INSTANCE.get("application.icon.path")));

			trayIcon = new TrayIcon(iconImage);

			trayIcon.setImageAutoSize(true);
			trayIcon.setPopupMenu(trayPopup.get());
		}
	}

	@Override
	public void windowIconified(final WindowEvent e) {
		super.windowIconified(e);

		if (useTray) {
			minimizeFrameIntoTray((JFrame) e.getSource());
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (useTray && minimizeFrameIntoTray((JFrame) e.getSource())) {
			return;
		}

		mainFrame.close();
	}

	/**
	 * Minimizes the specified frame into the tray. When tray icon receives
	 * an action, the frame is restored to the previous state.
	 *
	 * @return a boolean value whether the window has been minimized or not.
	 */
	private boolean minimizeFrameIntoTray(final JFrame frame) {
		if (!useTray) {
			LOGGER.warn("It is not allowed to use the system tray");

			return false;
		}

		final SystemTray tray = SystemTray.getSystemTray();

		/* Displays the window when the icon is clicked twice */
		trayIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int state = frame.getExtendedState();
				state &= ~Frame.ICONIFIED;

				frame.setVisible(true);
				frame.setExtendedState(state);
				tray.remove(trayIcon);

				trayIcon.removeActionListener(this);
			}
		});

		try {
			tray.add(trayIcon);
			frame.setVisible(false);
		} catch (AWTException ex) {
			LOGGER.error("Couldn't create a tray icon, the minimizing is not possible", ex);

			return false;
		}

		return true;
	}
}
