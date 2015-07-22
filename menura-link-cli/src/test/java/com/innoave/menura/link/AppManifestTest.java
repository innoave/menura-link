/**
 * @(#) $Id: AppManifestTest.java 11218 2015-02-26 13:29:40Z haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: AppManifestTest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link;

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
 * @version $Revision: 11218 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
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
