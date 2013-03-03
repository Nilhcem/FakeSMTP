package com.nilhcem.fakesmtp.integration;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.test.TestConfig;

public final class SendEmailsIT {
	private static final Logger logger = LoggerFactory.getLogger(SendEmailsIT.class);

	@BeforeClass
	public static void displayInfo() {
		logger.info("Launching integration tests...");
		logger.info("You need to run the project and launch the SMTP server on port {} before testing.", TestConfig.PORT_INTEGRATION_TESTS);
	}

	@Test
	public void sendSimpleTestEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(TestConfig.HOST);
		email.setSmtpPort(TestConfig.PORT_INTEGRATION_TESTS);
		email.setStartTLSEnabled(true);
		email.setFrom("user@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail... :-)");
		email.addTo("foo@bar.com");
		email.send();
	}

	@Test
	public void sendEmailWithAttachment() throws EmailException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("src/main/resources" + Configuration.INSTANCE.get("application.icon.path"));
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Image file");
		attachment.setName("icon.gif");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(TestConfig.HOST);
		email.setSmtpPort(TestConfig.PORT_INTEGRATION_TESTS);
		email.addTo("jdoe@somewhere.org", "John Doe");
		email.setFrom("me@apache.org", "Me");
		email.setSubject("The file");
		email.setMsg("Here is the file you wanted.");

		// Add the attachment
		email.attach(attachment);

		// Send the email
		email.send();
	}

	@Test
	public void sendHTMLFormattedEmail() throws EmailException {
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		email.setHostName(TestConfig.HOST);
		email.setSmtpPort(TestConfig.PORT_INTEGRATION_TESTS);
		email.addTo("jdoe@somewhere.org", "John Doe");
		email.setFrom("me@apache.org", "Me");
		email.setSubject("Test HTML email.");

		// Set the HTML message
		email.setHtmlMsg("<html><body>This is a<br /><b>Test</b> email.</body></html>");

		// Set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// Send the email
		email.send();
	}
}
