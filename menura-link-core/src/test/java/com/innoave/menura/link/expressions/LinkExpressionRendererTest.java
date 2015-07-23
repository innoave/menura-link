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
package com.innoave.menura.link.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkExpressionRendererTest {

	private static String nl;
	
	private LinkExpressionRenderer renderer;
	
	
	@BeforeClass
	public static void setupOnce() {
		nl = System.getProperty("line.separator");
	}
	
	@Before
	public void setUp() {
		renderer = new LinkExpressionRenderer();
	}
	
	
	@Test
	public void testRender_NoAttributes() {
		
		String template = "---TEST-SETUP---\n"
				+ "test.step.name=Parameterized Request 101\n"
				+ "message.handler=com.innoave.menura.link.test.handler.TestRequestHandler\n"
				+ "system.adapter=com.innoave.menura.link.test.adapter.TestSystemAdapter\n"
				+ "---MESSAGE-HEADER---\n"
				+ "applikation=Menura\n"
				+ "funktion=Link\n"
				+ "aktion=ParameterizedRequest101\n"
				+ "correlationId=$correlationId$\n"
				+ "---MESSAGE-BODY---\n"
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
				+ "<request101>\n"
				+ "  <requestDTO>\n"
				+ "    <meldungsReferenz>$referenz$</meldungsReferenz>\n"
				+ "    <buchungsDatum>2014-09-22+02:00</buchungsDatum>\n"
				+ "    <belegDatum>2014-09-10+02:00</belegDatum>\n"
				+ "    <freigabeVon>Hrn. Hofrat</freigabeVon>\n"
				+ "    <bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>\n"
				+ "  </requestDTO>\n"
				+ "</request101>";
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		
		String result = renderer.render(template, attributes);
		
		String expected = "---TEST-SETUP---" + nl
				+ "test.step.name=Parameterized Request 101" + nl
				+ "message.handler=com.innoave.menura.link.test.handler.TestRequestHandler" + nl
				+ "system.adapter=com.innoave.menura.link.test.adapter.TestSystemAdapter" + nl
				+ "---MESSAGE-HEADER---" + nl
				+ "applikation=Menura" + nl
				+ "funktion=Link" + nl
				+ "aktion=ParameterizedRequest101" + nl
				+ "correlationId=" + nl
				+ "---MESSAGE-BODY---" + nl
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + nl
				+ "<request101>" + nl
				+ "  <requestDTO>" + nl
				+ "    <meldungsReferenz></meldungsReferenz>" + nl
				+ "    <buchungsDatum>2014-09-22+02:00</buchungsDatum>" + nl
				+ "    <belegDatum>2014-09-10+02:00</belegDatum>" + nl
				+ "    <freigabeVon>Hrn. Hofrat</freigabeVon>" + nl
				+ "    <bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>" + nl
				+ "  </requestDTO>" + nl
				+ "</request101>";
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testRender_WithAttributes() {
		
		String template = "---TEST-SETUP---\n"
				+ "test.step.name=Parameterized Request 101\n"
				+ "message.handler=com.innoave.menura.link.test.handler.TestRequestHandler\n"
				+ "system.adapter=com.innoave.menura.link.test.adapter.TestSystemAdapter\n"
				+ "---MESSAGE-HEADER---\n"
				+ "applikation=Menura\n"
				+ "funktion=Link\n"
				+ "aktion=ParameterizedRequest101\n"
				+ "correlationId=$correlationId$\n"
				+ "---MESSAGE-BODY---\n"
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
				+ "<request101>\n"
				+ "  <requestDTO>\n"
				+ "    <meldungsReferenz>$referenz$</meldungsReferenz>\n"
				+ "    <buchungsDatum>2014-09-22+02:00</buchungsDatum>\n"
				+ "    <belegDatum>2014-09-10+02:00</belegDatum>\n"
				+ "    <freigabeVon>Hrn. Hofrat</freigabeVon>\n"
				+ "    <bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>\n"
				+ "  </requestDTO>\n"
				+ "</request101>";
		
		String correlationId = "wojhals879dkfj121";
		String referenz = "ref.1234/10-1";
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("correlationId", correlationId);
		attributes.put("referenz", referenz);
		
		String result = renderer.render(template, attributes);
		
		String expected = "---TEST-SETUP---" + nl
				+ "test.step.name=Parameterized Request 101" + nl
				+ "message.handler=com.innoave.menura.link.test.handler.TestRequestHandler" + nl
				+ "system.adapter=com.innoave.menura.link.test.adapter.TestSystemAdapter" + nl
				+ "---MESSAGE-HEADER---" + nl
				+ "applikation=Menura" + nl
				+ "funktion=Link" + nl
				+ "aktion=ParameterizedRequest101" + nl
				+ "correlationId=wojhals879dkfj121" + nl
				+ "---MESSAGE-BODY---" + nl
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + nl
				+ "<request101>" + nl
				+ "  <requestDTO>" + nl
				+ "    <meldungsReferenz>ref.1234/10-1</meldungsReferenz>" + nl
				+ "    <buchungsDatum>2014-09-22+02:00</buchungsDatum>" + nl
				+ "    <belegDatum>2014-09-10+02:00</belegDatum>" + nl
				+ "    <freigabeVon>Hrn. Hofrat</freigabeVon>" + nl
				+ "    <bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>" + nl
				+ "  </requestDTO>" + nl
				+ "</request101>";
		
		assertEquals(expected, result);
	}
	
}
