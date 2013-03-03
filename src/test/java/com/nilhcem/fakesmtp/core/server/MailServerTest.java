package com.nilhcem.fakesmtp.core.server;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.MailSaver;

public class MailServerTest {
	private static MailSaver saver;

	@BeforeClass
	public static void createMailSaver() {
		saver = new MailSaver();
	}

	@Test
	public void testGetLock() {
		assertSame(saver, saver.getLock());
	}

	@Test
	public void testSaveDeleteEmail() throws UnsupportedEncodingException, InterruptedException {
		final String from = "from@example.com";
		final String to = "to@example.com";
		final String subject = "Hello";
		final String content = "How are you?";

		// Save
		final InputStream data = fromString(getMockEmail(from, to, subject, content));
		Observer mockObserver = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				EmailModel model = (EmailModel)arg;

				assertEquals(from, model.getFrom());
				assertEquals(to, model.getTo());
				assertEquals(subject, model.getSubject());
				assertEquals(to, model.getTo());
				assertNotNull(model.getEmailStr());
				assertFalse(model.getEmailStr().isEmpty());
				assertNotNull(model.getFilePath());
				assertFalse(model.getFilePath().isEmpty());

				File file = new File(model.getFilePath());
				assertTrue(file.exists());

				// Delete
				UIModel.INSTANCE.getListMailsMap().put(0, model.getFilePath());
				saver.deleteEmails();
				assertFalse(file.exists());
			}
		};
		saver.addObserver(mockObserver);
		assertTrue(saver.countObservers() != 0);
		saver.saveEmailAndNotify(from, to, data);
		saver.deleteObserver(mockObserver);
	}

	private String getMockEmail(String from, String to, String subject, String content) {
		String br = System.getProperty("line.separator");

		StringBuilder sb = new StringBuilder()
			.append("Line 1 will be removed").append(br)
			.append("Line 2 will be removed").append(br)
			.append("Line 3 will be removed").append(br)
			.append("Line 4 will be removed").append(br)
			.append("Date: Thu, 15 May 2042 04:42:42 +0800 (CST)").append(br)
			.append(String.format("From: \"%s\" <%s>%n", from, from))
			.append(String.format("To: \"%s\" <%s>%n", to, to))
			.append("Message-ID: <17000042.0.1300000000042.JavaMail.wtf@OMG00042>").append(br)
			.append(String.format("Subject: %s%n", subject))
			.append("MIME-Version: 1.0").append(br)
			.append("Content-Type: text/plain; charset=us-ascii").append(br)
			.append("Content-Transfer-Encoding: 7bit").append(br).append(br)
			.append(content).append(br).append(br);
		return sb.toString();
	}

	private InputStream fromString(String str) throws UnsupportedEncodingException {
		byte[] bytes = str.getBytes(I18n.UTF8);
		return new ByteArrayInputStream(bytes);
	}
}
