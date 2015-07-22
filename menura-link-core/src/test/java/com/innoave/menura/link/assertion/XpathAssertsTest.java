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
