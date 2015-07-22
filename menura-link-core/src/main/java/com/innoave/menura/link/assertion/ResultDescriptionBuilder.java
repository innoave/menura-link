/**
 * @(#) $Id: ResultDescriptionBuilder.java,v 1.0 07.07.2015 10:54:48 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: ResultDescriptionBuilder.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
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
