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
package com.innoave.menura.link.assertion;

import com.innoave.menura.link.api.AssertResult;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class ResultDescriptionBuilder {
	
	
	public String buildDescription(
			final AssertResult.ResultCode resultCode,
			final String propertyName,
			final String expected,
			final String actual
			) {
		final StringBuilder sb = new StringBuilder();
		switch (resultCode) {
		case OK:
			sb.append("OK. XPath: ")
				.append(propertyName)
				.append(" : erwartet=")
				.append(expected)
				.append(" == aktuell=")
				.append(actual);
			break;
		case ERROR:
			sb.append("Fehler. XPath: ")
				.append(propertyName)
				.append(" unterschiedlich: erwartet=")
				.append(expected)
				.append(" <> aktuell=")
				.append(actual);
			break;
		case ATTRIBUTE:
			sb.append("Attribute. XPath: ")
				.append(propertyName)
				.append(" : ")
				.append(expected)
				.append(" := ")
				.append(actual);
			break;
		}
		return sb.toString();
	}

}
