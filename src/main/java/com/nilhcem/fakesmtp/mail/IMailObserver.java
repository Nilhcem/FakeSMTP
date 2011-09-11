package com.nilhcem.fakesmtp.mail;

import java.io.InputStream;

public interface IMailObserver {
	public void update(InputStream data);
}
