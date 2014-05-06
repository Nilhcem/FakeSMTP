package com.nilhcem.fakesmtp.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Component;

/**
 * Intercepts every uncaught exception.
 *
 * @author Nilhcem
 * @since 1.1
 * @see <a href="link">http://stuffthathappens.com/blog/2007/10/07/programmers-notebook-uncaught-exception-handlers/</a>
 */
public final class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	private Component parentComponent;
	private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

	/**
	 * Called when an uncaught exception is thrown. Will display the error message in a dialog box.
	 *
	 * @param t the thread where the exception was throws.
	 * @param e the thrown exception.
	 */
	@Override
	public void uncaughtException(final Thread t, final Throwable e) {
		try {
			if (SwingUtilities.isEventDispatchThread()) {
				showException(t, e);
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						showException(t, e);
					}
				});
			}
		} catch (Exception excpt) {
			LOGGER.error("", excpt);
		}
	}

	/**
	 * Sets the parent component where an error dialog might be displayed.
	 *
	 * @param parentComponent a component where an error dialog might be displayed.
	 */
	public void setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
	}

	/**
	 * Displays an error dialog describing the uncaught exception.
	 *
	 * @param t the thread where the exception was throws.
	 * @param e the thrown exception.
	 */
	private void showException(Thread t, Throwable e) {
		LOGGER.error("", e);
		String msg = String.format("Unexpected problem on thread %s: %s", t.getName(), e.getMessage());
		JOptionPane.showMessageDialog(parentComponent, msg);
	}
}
