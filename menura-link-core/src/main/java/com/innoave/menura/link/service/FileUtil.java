/**
 *  Copyright (c) 2015 Innoave.com
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
package com.innoave.menura.link.service;

import java.io.File;
import java.util.StringTokenizer;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class FileUtil {

	
	public static File searchFileOnClassPath(final String fileName) {
		File found = null;
		final String classpathStr = System.getProperty("java.class.path");
		final String pathSeparator = System.getProperty("path.separator");
		final StringTokenizer classpathTokenizer = new StringTokenizer(classpathStr, pathSeparator);
		while (classpathTokenizer.hasMoreElements()) {
			final String pathElementName = classpathTokenizer.nextToken();
			final File pathElement = new File(pathElementName);
			final File targetFile = pathElement.isFile()
					? new File(pathElement.getParent(), fileName)
					: new File(pathElement, fileName);
			if (targetFile.exists()) {
				found = targetFile;
				break;
			}
		}
		return found;
	}
	
}
