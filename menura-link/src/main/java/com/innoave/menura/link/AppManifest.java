/**
 * @(#) $Id: AppManifest.java,v 1.0 23.02.2015 11:44:10 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Changes:
 *
 * $Log: AppManifest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class AppManifest {

	
	public static String getVersion(final Class<?> applicationClazz) {
		try {
			final Manifest manifest = getManifest(applicationClazz);
			if (manifest != null) {
				final Attributes attr = manifest.getMainAttributes();
				if (attr != null) {
					final String version;
					version = attr.getValue("Implementation-Version");
					if (version != null) {
						return version;
					}
				}
			}
		} catch (Exception e) {
		}
		return "(unbekannt)";
	}
	
	public static Manifest getManifest(final Class<?> clazz) throws MalformedURLException, IOException {
		final String clazzPath = clazz.getResource(clazz.getSimpleName() + ".class").toExternalForm();
		final String manifestPath;
		if (clazzPath.startsWith("jar")) {
			manifestPath = clazzPath.substring(0, clazzPath.lastIndexOf('!') + 1)
					+ JarFile.MANIFEST_NAME;
		} else {
			final String relativePath = clazz.getName().replace('.', '/') + ".class";
			manifestPath = clazzPath.substring(0, clazzPath.length() - relativePath.length() - 1)
					+ "/" + JarFile.MANIFEST_NAME;
		}
		final Manifest manifest = new Manifest(new URL(manifestPath).openStream());
		return manifest;
	}

}
