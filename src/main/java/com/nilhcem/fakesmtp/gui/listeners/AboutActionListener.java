package com.nilhcem.fakesmtp.gui.listeners;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.I18n;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the About action.
 *
 * @author Vest
 * @since 2.1
 */
public class AboutActionListener implements ActionListener {

	private final I18n i18n = I18n.INSTANCE;
	private final Container parent;

	private static final Logger LOGGER = LoggerFactory.getLogger(AboutActionListener.class);

	/**
	 * @param parent The parent container that is used for the About dialog window.
	 */
	public AboutActionListener(Container parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// for copying style
		JLabel label = new JLabel();
		Font font = label.getFont();

		// create some css from the label's font
		StringBuffer style = new StringBuffer("font-family:").append(font.getFamily()).append(";font-weight:");
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
					AboutActionListener.launchUrl(e.getURL().toString());
				}
			}
		});
		ep.setEditable(false);
		ep.setBackground(label.getBackground());

		// show
		JOptionPane.showMessageDialog(parent, ep, String.format(i18n.get("menubar.about.title"),
			Configuration.INSTANCE.get("application.name")), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Opens a web browser to launch the URL specified in parameters.
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
