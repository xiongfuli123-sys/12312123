/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalization (i18n) support for the Simple Insurance application.
 * Loads localized strings from {@code Messages.properties} bundles.
 * The default language follows the JVM locale; call {@link #setLocale(Locale)}
 * to switch at runtime.
 */
public final class Messages {

	private static final String BUNDLE_NAME = "insurance.ui.messages.Messages";

	private static Locale currentLocale = Locale.getDefault();
	private static ResourceBundle bundle = loadBundle(currentLocale);

	private Messages() {
	}

	private static ResourceBundle loadBundle(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_NAME, locale, new UTF8Control());
	}

	/**
	 * Switch the active locale and reload the message bundle.
	 */
	public static synchronized void setLocale(Locale locale) {
		currentLocale = locale;
		bundle = loadBundle(locale);
	}

	public static Locale getLocale() {
		return currentLocale;
	}

	/**
	 * Get a localized string for the given key.
	 */
	public static String getString(String key) {
		if (bundle.containsKey(key)) {
			return bundle.getString(key);
		}
		return '!' + key + '!';
	}

	/**
	 * Get a localized formatted string.
	 */
	public static String getString(String key, Object... args) {
		String pattern = getString(key);
		return java.text.MessageFormat.format(pattern, args);
	}
}
