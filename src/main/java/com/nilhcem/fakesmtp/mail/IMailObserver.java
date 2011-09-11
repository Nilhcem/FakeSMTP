package com.nilhcem.fakesmtp.mail;

import java.io.InputStream;

public interface IMailObserver {
	void update(InputStream data);
}
