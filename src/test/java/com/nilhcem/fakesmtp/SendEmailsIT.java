package com.nilhcem.fakesmtp;

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

// see http://commons.apache.org/email/userguide.html
public class SendEmailsIT {
	private static final String SMTP_HOST = "localhost";
	private static final int SMTP_PORT = 2525;
	private static final Logger logger = LoggerFactory.getLogger(SendEmailsIT.class);

	@BeforeClass
	public static void displayInfo() {
		logger.info("Launching integration tests...");
		logger.info("You need to run the project and launch the SMTP server on port {} before testing.", SendEmailsIT.SMTP_PORT);
	}

	@Test
	public void sendSimpleTestEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(SendEmailsIT.SMTP_HOST);
		email.setSmtpPort(SendEmailsIT.SMTP_PORT);
		email.setTLS(true);
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
		attachment.setPath("src/main/resources/logback.xml");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Xml file");
		attachment.setName("logback.xml");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(SendEmailsIT.SMTP_HOST);
		email.setSmtpPort(SendEmailsIT.SMTP_PORT);
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
		email.setHostName(SendEmailsIT.SMTP_HOST);
		email.setSmtpPort(SendEmailsIT.SMTP_PORT);
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
