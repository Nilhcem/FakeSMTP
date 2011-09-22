package com.nilhcem.fakesmtp.core;

import static org.junit.Assert.*;
import java.util.Locale;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class I18nTest {
	private static Locale defaultLocale;

	@BeforeClass
	public static void initLocale() {
		defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.TAIWAN);
	}

	@AfterClass
	public static void resetLocale() {
		Locale.setDefault(defaultLocale);
	}

	@Test
	public void uniqueInstance() {
		I18n a = I18n.INSTANCE;
		I18n b = I18n.INSTANCE;
		assertSame(a, b);
	}

	@Test
	public void getEmptyValueWhenKeyIsNotFound() {
		assertTrue(I18n.INSTANCE.get("this.key.doesnt.exist").isEmpty());
	}

	@Test
	public void getValueWhenKeyIsFound() {
		assertTrue(!I18n.INSTANCE.get("menubar.file").isEmpty());
	}
}
