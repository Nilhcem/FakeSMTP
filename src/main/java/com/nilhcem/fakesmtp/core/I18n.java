package com.nilhcem.fakesmtp.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializes resource bundle and get messages from keys.
 * <p>
 * This class will be instantiated only once and will use the JVM's default locale.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 */
public enum I18n {
	INSTANCE;

	public static final String UTF8 = "UTF-8";
	private static final String RESOURCE_FILE = "i18n/messages";
	private final Logger logger = LoggerFactory.getLogger(I18n.class);
	private final ResourceBundle resources;

	/**
	 * Initializes resource bundle with the JVM's default locale.
	 * <p>
	 * If the JVM's default locale doesn't have any resource file, will take the en_US resources instead.
	 * </p>
	 */
	I18n() {
		ResourceBundle bundle;

		try {
			bundle = ResourceBundle.getBundle(I18n.RESOURCE_FILE, Locale.getDefault());
		} catch (MissingResourceException mre) {
			logger.error("{}", mre.getMessage());
			logger.info("Will use default bundle (en_US) instead");
			bundle = ResourceBundle.getBundle(I18n.RESOURCE_FILE, Locale.US);
		}
		resources = bundle;
	}

	/**
	 * Returns the resource for the key passed in parameters.
	 * <p>
	 * If the key is not found, returns an empty string.
	 * </p>
	 *
	 * @param key a String representing the key we want to get the resource from.
	 * @return The text corresponding to the key passed in parameters, or an empty string if not found.
	 */
	public String get(String key) {
		try {
			return resources.getString(key);
		} catch (MissingResourceException e) {
			logger.error("{}", e.getMessage());
			return "";
		}
	}
}
