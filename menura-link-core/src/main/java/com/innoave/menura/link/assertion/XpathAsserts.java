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

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class XpathAsserts {
	
	public static boolean assertXpathEquals(final String xpath, final Document doc, final String expected) {
		boolean equal;
		try {
			final String actual;
			actual = _evaluateXpath(xpath, doc);
			equal = expected.equals(actual);
		} catch (XPathExpressionException e) {
			equal = false;
		}
		return equal;
	}
	
	public static boolean assertXpathExists(final String xpath, final Document doc) {
		boolean exists;
		try {
			final Object actual;
			actual = _evaluateXpath(xpath, doc, XPathConstants.NODE);
			exists = actual != null;
		} catch (XPathExpressionException e) {
			exists = false;
		}
		return exists;
	}
	
	public static boolean assertXpathNotEmpty(final String xpath, final Document doc) {
		boolean notEmpty;
		try {
			final String actual;
			actual = _evaluateXpath(xpath, doc);
			notEmpty = actual != null && !actual.isEmpty();
		} catch (XPathExpressionException e) {
			notEmpty = false;
		}
		return notEmpty;
	}
	
	public static String evaluateXpath(final String xpath, final Document doc) throws XPathExpressionException {
		return _evaluateXpath(xpath, doc);
	}
	
	private static String _evaluateXpath(final String xpath, final Document doc) throws XPathExpressionException {
		final XPathFactory xpf = XPathFactory.newInstance();
		final XPath xp = xpf.newXPath();
		final String actual;
		actual = xp.evaluate(xpath, doc);
		return actual;
	}
	
	private static Object _evaluateXpath(final String xpath, final Document doc, final QName qname) throws XPathExpressionException {
		final XPathFactory xpf = XPathFactory.newInstance();
		final XPath xp = xpf.newXPath();
		final Object actual;
		actual = xp.evaluate(xpath, doc, qname);
		return actual;
	}

}
