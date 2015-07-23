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

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.jar.Manifest;

import org.junit.Test;

import com.innoave.menura.link.cli.AppManifest;
import com.innoave.menura.link.cli.Main;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class AppManifestTest {

	@Test
	public void testGetManifest() throws MalformedURLException, IOException {
		try {
			Manifest manifest = AppManifest.getManifest(AppManifestTest.class);
			assertNotNull(manifest);
			assertEquals(Main.class.getName(), manifest.getMainAttributes().getValue("Main-Class"));
		} catch (FileNotFoundException e) {
			//Test Funktioniert nur in IDE, wenn das MANIFEST.MF im Classpath ist
		}
	}
	
	@Test
	public void testGetVersion() {
		String version = AppManifest.getVersion(AppManifestTest.class);
		if (!"(unbekannt)".equals(version)) {
			assertEquals(5, version.substring(0, version.lastIndexOf("-SNAPSHOT")).length());
		} else {
			//Test Funktioniert nur in IDE, wenn das MANIFEST.MF im Classpath ist
		}
	}
	
}
