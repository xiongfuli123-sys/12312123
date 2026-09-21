/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * A {@link ResourceBundle.Control} that reads {@code .properties} files as
 * UTF-8 instead of the default ISO-8859-1, so that Chinese characters can be
 * stored directly without {@code \\u} escapes.
 */
public class UTF8Control extends ResourceBundle.Control {

	/**
	 * Do not fall back to the JVM default locale. Otherwise switching to
	 * English on a Chinese OS would still load {@code Messages_zh.properties}.
	 * Returning {@code null} ends the chain at the root {@code Messages.properties}.
	 */
	@Override
	public Locale getFallbackLocale(String baseName, Locale locale) {
		return null;
	}

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format,
			ClassLoader loader, boolean reload) throws IllegalAccessException,
			InstantiationException, IOException {
		if (!"java.properties".equals(format)) {
			return null;
		}
		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, "properties");
		ResourceBundle bundle = null;
		InputStream stream = null;
		if (reload) {
			URL url = loader.getResource(resourceName);
			if (url != null) {
				URLConnection connection = url.openConnection();
				if (connection != null) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		} else {
			stream = loader.getResourceAsStream(resourceName);
		}
		if (stream != null) {
			try {
				bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
			} finally {
				stream.close();
			}
		}
		return bundle;
	}
}
