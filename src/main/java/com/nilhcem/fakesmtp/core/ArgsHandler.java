
package com.nilhcem.fakesmtp.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.nilhcem.fakesmtp.model.UIModel;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

	private static final String OPT_BACKGROUNDSTART_SHORT = "b";
	private static final String OPT_BACKGROUNDSTART_LONG = "background";
	private static final String OPT_BACKGROUNDSTART_DESC = "If specified, does not start the GUI. Must be used with the -" + OPT_AUTOSTART_SHORT + " (--" +  OPT_AUTOSTART_LONG + ") argument";

	private static final String OPT_RELAYDOMAINS_SHORT = "r";
	private static final String OPT_RELAYDOMAINS_LONG = "relay-domains";
	private static final String OPT_RELAYDOMAINS_DESC = "Comma separated email domain(s) for which relay is accepted. If not specified, relays to any domain. If specified, relays only emails matching these domain(s), dropping (not saving) others";
	private static final String OPT_RELAYDOMAINS_SEPARATOR = ",";

	private static final String OPT_MEMORYMODE_SHORT = "m";
	private static final String OPT_MEMORYMODE_LONG = "memory-mode";
	private static final String OPT_MEMORYMODE_DESC = "Disable the persistence in order to avoid the overhead that it adds";

	private static final String OPT_BINDADDRESS_SHORT = "a";
	private static final String OPT_BINDADDRESS_LONG = "bind-address";
	private static final String OPT_BINDADDRESS_DESC = "IP address or hostname to bind to. Binds to all local IP addresses if not specified. Only works together with the -" + OPT_BACKGROUNDSTART_SHORT + " (--" +  OPT_BACKGROUNDSTART_LONG + ") argument.";

	private static final String OPT_EMLVIEWER_SHORT = "e";
	private static final String OPT_EMLVIEWER_LONG = "eml-viewer";
	private static final String OPT_EMLVIEWER_DESC = "Executable of program used for viewing emails";

	private final Options options;

	private String port;
	private String bindAddress;
	private String outputDirectory;
	private String emlViewer;
	private boolean backgroundStart;
	private boolean startServerAtLaunch;
	private boolean memoryModeEnabled;

	/**
	 * Handles command line arguments.
	 */
	ArgsHandler() {
		options = new Options();
		options.addOption(OPT_EMAILS_DIR_SHORT, OPT_EMAILS_DIR_LONG, true, OPT_EMAILS_DESC);
		options.addOption(OPT_AUTOSTART_SHORT, OPT_AUTOSTART_LONG, false, OPT_AUTOSTART_DESC);
		options.addOption(OPT_PORT_SHORT, OPT_PORT_LONG, true, OPT_PORT_DESC);
		options.addOption(OPT_BINDADDRESS_SHORT, OPT_BINDADDRESS_LONG, true, OPT_BINDADDRESS_DESC);
		options.addOption(OPT_BACKGROUNDSTART_SHORT, OPT_BACKGROUNDSTART_LONG, false, OPT_BACKGROUNDSTART_DESC);
		options.addOption(OPT_RELAYDOMAINS_SHORT, OPT_RELAYDOMAINS_LONG, true, OPT_RELAYDOMAINS_DESC);
		options.addOption(OPT_MEMORYMODE_SHORT, OPT_MEMORYMODE_LONG, false, OPT_MEMORYMODE_DESC);
		options.addOption(OPT_EMLVIEWER_SHORT, OPT_EMLVIEWER_LONG, true, OPT_EMLVIEWER_DESC);
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

		outputDirectory = cmd.getOptionValue(OPT_EMAILS_DIR_SHORT);
		if (outputDirectory != null) {
			UIModel.INSTANCE.setSavePath(outputDirectory);
		}

		port = cmd.getOptionValue(OPT_PORT_SHORT);
		bindAddress = cmd.getOptionValue(OPT_BINDADDRESS_SHORT);
		startServerAtLaunch = cmd.hasOption(OPT_AUTOSTART_SHORT);
		backgroundStart = cmd.hasOption(OPT_BACKGROUNDSTART_SHORT);
		memoryModeEnabled = cmd.hasOption(OPT_MEMORYMODE_SHORT);
		emlViewer = cmd.getOptionValue(OPT_EMLVIEWER_SHORT);

		// Change SMTP server log level to info if memory mode was enabled to improve performance
		if (memoryModeEnabled) {
			((Logger) LoggerFactory.getLogger(org.subethamail.smtp.server.Session.class)).setLevel(Level.INFO);
		}

		String relaydomains = cmd.getOptionValue(OPT_RELAYDOMAINS_SHORT);
		if (relaydomains != null) {
			List<String> domains = new ArrayList<String>();
			for (String domain : Arrays.asList(relaydomains.split(OPT_RELAYDOMAINS_SEPARATOR))) {
				domains.add(domain.trim());
			}
			UIModel.INSTANCE.setRelayDomains(domains);
		}

		// Host binding for GUI
		if (bindAddress != null) {
			UIModel.INSTANCE.setHost(bindAddress);
		}
	}

	/**
	 * Displays the app's usage in the standard output.
	 */
	public void displayUsage() {
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
	 * @return whether or not the SMTP server must be running without a GUI, only if started at launch (if {@code shouldStartServerAtLaunch()} returns true}).
	 * @see #shouldStartServerAtLaunch
	 */
	public boolean shouldStartInBackground() {
		return startServerAtLaunch && backgroundStart;
	}

	/**
	 * @return the port, as specified by the user, or a {@code null} string if unspecified.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return the bind address, as specified by the user, or a {@code null} string if unspecified.
	 */
	public String getBindAddress() {
		return bindAddress;
	}

	/**
	 * @return the output directory, as specified by the user, or a {@code null} string if unspecified.
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @return whether or not the SMTP server should disable the persistence in order to avoid the overhead that it adds.
	 * This is particularly useful when we launch performance tests that massively send emails.
	 */
	public boolean memoryModeEnabled() {
		return memoryModeEnabled;
	}

	/**
	 * @return the name of executable used for viewing eml files, as specified by the user, or a {@code null} string if unspecified.
	 */
	public String getEmlViewer() {
		return emlViewer;
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
