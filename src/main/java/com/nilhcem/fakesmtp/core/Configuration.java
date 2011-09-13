package com.nilhcem.fakesmtp.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.LoggerFactory;

// Gets some project specific configuration variables.
public enum Configuration {
	INSTANCE;

	private static final String CONFIG_FILE = "/configuration.properties";
	private final Properties config = new Properties();

	private Configuration() {
		InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			LoggerFactory.getLogger(Configuration.class).error("", e);
		}
	}

	// Returns the key or an empty string if not found
	public String get(String key) {
		return ((config != null && config.containsKey(key)) ? (String)config.get(key) : "");
	}
}
