package com.nilhcem.fakesmtp.server;

import java.io.InputStream;

public interface IMailObserver {
	void update(InputStream data);
}
