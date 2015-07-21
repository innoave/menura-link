/**
 * @(#) $Id: XpathAssertsTest.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: XpathAssertsTest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.assertion;

import static org.junit.Assert.*;
import static com.innoave.menura.link.assertion.XpathAsserts.*;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class XpathAssertsTest {

	
	
	private Document readDocumentFromString(final String xmlContent) throws SAXException, IOException, ParserConfigurationException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final StringReader stringReader = new StringReader(xmlContent);
		try {
			final Document doc;
			doc = db.parse(new InputSource(stringReader));
			return doc;
		} finally {
			stringReader.close();
		}
	}
	
	@Test
	public void testAssertXpathEquals_isEqual() throws Exception {
		String xml = "<auto><marke>Volvo</marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathEquals("/auto/marke", dom, "Volvo");
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathEquals_emptyTag() throws Exception {
		String xml = "<auto><marke></marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathEquals("/auto/marke", dom, "");
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathEquals_tagIsEmpty() throws Exception {
		String xml = "<auto><marke></marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathEquals("/auto/marke", dom, "Volvo");
		
		assertFalse(equals);
	}
	
	@Test
	public void testAssertXpathEquals_tagNotExisting() throws Exception {
		String xml = "<auto><name></name></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathEquals("/auto/marke", dom, "Volvo");
		
		assertFalse(equals);
	}
	
	@Test
	public void testAssertXpathExists_tagIsExisting() throws Exception {
		String xml = "<auto><marke></marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathExists("/auto/marke", dom);
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathExists_tagIsExisting_multipleSiblings() throws Exception {
		String xml = "<auto><spec>" +
					"<type></type>" +
					"<marke></marke>" +
					"<color></color>" +
				"</spec></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathExists("/auto/spec/marke", dom);
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathExists_tagIsExisting_multipleTags() throws Exception {
		String xml = "<auto><spec>" +
					"<type></type>" +
					"<marke></marke>" +
					"<marke></marke>" +
					"<color></color>" +
				"</spec></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathExists("/auto/spec/marke", dom);
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathExists_tagIsNotExisting() throws Exception {
		String xml = "<auto><spec><marke></marke></spec></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathExists("/auto/marke", dom);
		
		assertFalse(equals);
	}
	
	@Test
	public void testAssertXpathNotEmpty_tagHasValue() throws Exception {
		String xml = "<auto><marke>Volvo</marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathNotEmpty("/auto/marke", dom);
		
		assertTrue(equals);
	}
	
	@Test
	public void testAssertXpathNotEmpty_tagIsEmpty() throws Exception {
		String xml = "<auto><marke></marke></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathNotEmpty("/auto/marke", dom);
		
		assertFalse(equals);
	}
	
	@Test
	public void testAssertXpathNotEmpty_tagIsNotExisting() throws Exception {
		String xml = "<auto><spec><marke></marke></spec></auto>";
		Document dom = readDocumentFromString(xml);
		
		boolean equals;
		equals = assertXpathNotEmpty("/auto/marke", dom);
		
		assertFalse(equals);
	}
	
}
