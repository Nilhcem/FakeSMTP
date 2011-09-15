package com.nilhcem.fakesmtp.gui.tab;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.gui.info.ClearAllButton;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.MailSaver;

public final class MailsListPane implements Observer {
	private int nbElements = 0;
	private Desktop desktop = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(MailsListPane.class);
	private final JScrollPane mailsListPane = new JScrollPane();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
	private final int[] widths = new int[] { 85, 140, 140}; //widths of columns in tab

	private final JTable table = new JTable() {
		private static final long serialVersionUID = 6332956458868628779L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private final DefaultTableModel model = new DefaultTableModel() {
		private static final long serialVersionUID = -6716294637919469299L;

		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	public MailsListPane() {
		// Init desktop (Java 6 Desktop API)
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && desktop != null) {
					JTable target = (JTable) e.getSource();
					String fileName = UIModel.INSTANCE.getListMailsMap().get(target.getSelectedRow());
					if (fileName == null) {
						LOGGER.error("Can't file any associated email for row #{}", target.getSelectedRow());
					}

					File file = new File(fileName);
					if (file.exists()) {
						try {
							desktop.open(file);
						} catch (IOException ioe) {
							LOGGER.error("", ioe);
							displayError("Can't open the file: "  + file.getAbsolutePath());
						}
					} else {
						displayError("Can't find the file: "  + file.getAbsolutePath());
					}
				}
			}
		});

		// Auto scroll tab to bottom when a new element is inserted
		table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				table.scrollRectToVisible(new Rectangle(table.getCellRect(nbElements, 0, true)));
			}
		});

		model.addColumn("Received");
		model.addColumn("From");
		model.addColumn("To");
		model.addColumn("Subject");
		table.setModel(model);

		mailsListPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// When the width of a column is changed, only the columns to the left and right of the margin change
				table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

				// Set width for each column
				int total = 0;
				int length = widths.length;
				for (int i = 0; i < length; i++) {
					table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
					total += widths[i];
				}
				table.getColumnModel().getColumn(length).setPreferredWidth(table.getWidth() - total);
			}
		});
		mailsListPane.getViewport().add(table, null);
	}

	public JScrollPane get() {
		return mailsListPane;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MailSaver) {
			EmailModel email = (EmailModel) arg;
			model.addRow(new Object[] { dateFormat.format(email.getReceivedDate()),
					email.getFrom(), email.getTo(), email.getSubject() });
			UIModel.INSTANCE.getListMailsMap().put(nbElements++, email.getFilePath());
		} else if (o instanceof ClearAllButton) {
			// Delete information from the map
			UIModel.INSTANCE.getListMailsMap().clear();

			// Remove elements from the list
			try {
				while (nbElements > 0) {
					model.removeRow(--nbElements);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				LOGGER.error("", e);
			}
		}
	}

	// Displays a message dialog containing the error specified in parameter.
	private void displayError(String error) {
		JOptionPane.showMessageDialog(mailsListPane.getParent(), error, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
