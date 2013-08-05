package com.nilhcem.fakesmtp.core;

import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.nilhcem.fakesmtp.model.UIModel;

/**
 * Handles command line arguments.
 *
 * @author Nilhcem
 * @since 1.3
 */
public enum ArgsHandler {
	INSTANCE;

	private static final String OPT_EMAILS_DIR_SHORT = "o";
	private static final String OPT_EMAILS_DIR_LONG = "output-dir";
	private static final String OPT_EMAILS_DESC = "Emails output directory";

	private static final String OPT_AUTOSTART_SHORT = "s";
	private static final String OPT_AUTOSTART_LONG = "start-server";
	private static final String OPT_AUTOSTART_DESC = "Automatically starts the SMTP server at launch";

	private static final String OPT_PORT_SHORT = "p";
	private static final String OPT_PORT_LONG = "port";
	private static final String OPT_PORT_DESC = "SMTP port number";

	private final Options options;

	private boolean startServerAtLaunch;
	private String port;

	/**
	 * Handles command line arguments.
	 */
	private ArgsHandler() {
		options = new Options();
		options.addOption(OPT_EMAILS_DIR_SHORT, OPT_EMAILS_DIR_LONG, true, OPT_EMAILS_DESC);
		options.addOption(OPT_AUTOSTART_SHORT, OPT_AUTOSTART_LONG, false, OPT_AUTOSTART_DESC);
		options.addOption(OPT_PORT_SHORT, OPT_PORT_LONG, true, OPT_PORT_DESC);
	}

	/**
	 * Interprets command line arguments.
	 *
	 * @param args program's arguments.
	 * @throws ParseException when arguments are invalid.
	 */
	public void handleArgs(String[] args) throws ParseException {
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

		String outputDir = cmd.getOptionValue(OPT_EMAILS_DIR_SHORT);
		if (outputDir != null) {
			UIModel.INSTANCE.setSavePath(outputDir);
		}

		port = cmd.getOptionValue(OPT_PORT_SHORT);
		startServerAtLaunch = cmd.hasOption(OPT_AUTOSTART_SHORT);
	}

	/**
	 * Displays the app's usage in the standard output.
	 *
	 * @param exception an exception that contains an error message to display, or {@code null} if none.
	 */
	public void displayUsage(ParseException exception) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(String.format(Locale.US, "java -jar %s [OPTION]...", getJarName()), options);
	}

	/**
	 * @return whether or not the SMTP server must be started automatically at launch.
	 */
	public boolean shouldStartServerAtLaunch() {
		return startServerAtLaunch;
	}

	/**
	 * @return the port, as specified by the user, or a {@code null} string if unspecified.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return the file name of the program.
	 */
	private String getJarName() {
		return new java.io.File(
				ArgsHandler.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath())
		.getName();
	}
}
