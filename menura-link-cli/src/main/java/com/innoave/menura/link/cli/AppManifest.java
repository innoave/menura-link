/**
 *  Copyright (c) 2014 Innoave.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.innoave.menura.link.cli;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 *
 *
 * @author haraldmaida
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
