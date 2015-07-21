/**
 * @(#) $Id: XpathAsserts.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: XpathAsserts.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
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
