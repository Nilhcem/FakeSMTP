package com.nilhcem.fakesmtp.core.server;

import static org.junit.Assert.*;
import org.junit.Test;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public class SMTPServerHandlerTest {
	@Test
	public void uniqueInstance() {
		SMTPServerHandler a = SMTPServerHandler.INSTANCE;
		SMTPServerHandler b = SMTPServerHandler.INSTANCE;
		assertSame(a, b);
	}

	@Test(expected = OutOfRangePortException.class)
	public void testOutOfRangePort() throws BindPortException, OutOfRangePortException {
		SMTPServerHandler.INSTANCE.startServer(9999999, null);
	}

	@Test
	public void stopShouldDoNothingIfServerIsAlreadyStopped() {
		SMTPServerHandler.INSTANCE.stopServer();
		SMTPServerHandler.INSTANCE.stopServer();
		SMTPServerHandler.INSTANCE.stopServer();
	}
}
