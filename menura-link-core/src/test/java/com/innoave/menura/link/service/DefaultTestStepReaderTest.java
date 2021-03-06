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
package com.innoave.menura.link.service;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.TestStep;
import com.innoave.menura.link.service.DefaultTestStepReader;
import com.innoave.menura.link.test.adapter.TestSystemAdapter;
import com.innoave.menura.link.test.handler.TestRequestHandler;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class DefaultTestStepReaderTest {
	
	static final String REQUEST_101 = "com/innoave/menura/link/service/Request101.lts";

	private DefaultTestStepReader reader;
	
	
	@Before
	public void setUp() {
		this.reader = new DefaultTestStepReader();
	}
	
	
	@Test
	public void testReadTestStep_Request_FromInputStream() throws Exception {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final InputStream in = cl.getResourceAsStream(REQUEST_101);
		
		TestStep testStep;
		testStep = reader.readTestStep(in);
		
		assertEquals("Request 101", testStep.getName());
		assertEquals(FunctionalType.ACTION, testStep.getType());
		assertEquals(TestSystemAdapter.class.getName(), testStep.getSystemAdapter().getName());
		assertEquals(TestRequestHandler.class.getName(), testStep.getMessageHandler().getName());
		
		String vergleichsInhalt = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
				+ "<request101>\n"
					+ "<requestDTO>\n"
						+ "<meldungsReferenz>ref.1234/10-1</meldungsReferenz>\n"
						+ "<buchungsDatum>2014-09-22+02:00</buchungsDatum>\n"
						+ "<belegDatum>2014-09-10+02:00</belegDatum>\n"
						+ "<freigabeVon>Hrn. Hofrat</freigabeVon>\n"
						+ "<bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>\n"
					+ "</requestDTO>\n"
				+ "</request101>\n";
		
		LinkMessage linkMessage;
		linkMessage = testStep.getMessage();
		assertEquals("Menura", linkMessage.getApplikation());
		assertEquals("Link", linkMessage.getFunktion());
		assertEquals("Request101", linkMessage.getAktion());
		assertEquals("wojhals879dkfj121", linkMessage.getCorrelationId());
		assertEquals(vergleichsInhalt, linkMessage.getInhalt());
	}
	
}
