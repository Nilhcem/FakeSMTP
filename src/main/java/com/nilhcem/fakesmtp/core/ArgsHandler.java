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

	private final Options options;

	/**
	 * Opens the "{@code configuration.properties}" file and maps data.
	 */
	private ArgsHandler() {
		options = new Options();
		options.addOption(OPT_EMAILS_DIR_SHORT, OPT_EMAILS_DIR_LONG, true, OPT_EMAILS_DESC);
	}

	/**
	 * Interprets command line arguments.
	 *
	 * @param args Program's arguments.
	 * @throws ParseException when arguments are invalid.
	 */
	public void handleArgs(String[] args) throws ParseException {
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args);

		String outputDir = cmd.getOptionValue(OPT_EMAILS_DIR_SHORT);
		if (outputDir != null) {
			UIModel.INSTANCE.setSavePath(outputDir);
		}
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

	private String getJarName() {
		return new java.io.File(
				ArgsHandler.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath())
		.getName();
	}
}
