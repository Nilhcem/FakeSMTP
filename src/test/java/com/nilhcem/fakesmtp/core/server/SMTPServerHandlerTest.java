package com.nilhcem.fakesmtp.core.server;

import static org.junit.Assert.*;
import org.junit.Test;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;

public class SMTPServerHandlerTest {
	@Test
	public void uniqueInstance() {
		SMTPServerHandler a = SMTPServerHandler.get();
		SMTPServerHandler b = SMTPServerHandler.get();
		assertSame(a, b);
	}

	@Test(expected = OutOfRangePortException.class)
	public void testOutOfRangePort() throws BindPortException, OutOfRangePortException {
		SMTPServerHandler.get().startServer(9999999, null);
	}

	@Test
	public void stopShouldDoNothingIfServerIsAlreadyStopped() {
		SMTPServerHandler.get().stopServer();
		SMTPServerHandler.get().stopServer();
		SMTPServerHandler.get().stopServer();
	}
}
