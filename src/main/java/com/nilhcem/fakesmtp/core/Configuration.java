package com.nilhcem.fakesmtp.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * Contains and returns some project-specific configuration variables.
 *
 * @author Nilhcem
 * @since 1.0
 */
public enum Configuration {
	INSTANCE;

	private static final String CONFIG_FILE = "/configuration.properties";
	private final Properties config = new Properties();

	/**
	 * Opens the "{@code configuration.properties}" file and maps data.
	 */
	private Configuration() {
		InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			LoggerFactory.getLogger(Configuration.class).error("", e);
		}
	}

	/**
	 * Returns the value of a specific entry from the "{@code configuration.properties}" file.
	 *
	 * @param key a string representing the key from a key/value couple.
	 * @return the value of the key, or an empty string if the key was not found.
	 */
	public String get(String key) {
		if (config != null && config.containsKey(key)) {
			return config.getProperty(key);
		}
		return "";
	}
}
