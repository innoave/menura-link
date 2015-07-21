/**
 * @(#) $Id: DefaultTestStepReaderTest.java 10083 2014-12-04 14:43:28Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: DefaultTestStepReaderTest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
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
 * @version $Revision: 10083 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class DefaultTestStepReaderTest {
	
	static final String TEST_STEP_101 = "com/innoave/menura/link/service/TestStep101.lts";

	private DefaultTestStepReader reader;
	
	
	@Before
	public void setUp() {
		this.reader = new DefaultTestStepReader();
	}
	
	
	@Test
	public void testReadTestStep_FromInputStream() throws Exception {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final InputStream in = cl.getResourceAsStream(TEST_STEP_101);
		
		TestStep testStep;
		testStep = reader.readTestStep(in);
		
		assertEquals("Test Step 101", testStep.getName());
		assertEquals(FunctionalType.ACTION, testStep.getType());
		assertEquals(TestSystemAdapter.class.getName(), testStep.getSystemAdapter().getName());
		assertEquals(TestRequestHandler.class.getName(), testStep.getMessageHandler().getName());
		
		String vergleichsInhalt = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
				+ "<testStep101>\n"
					+ "<testStepDTO>\n"
						+ "<meldungsReferenz>ref.1234/10-1</meldungsReferenz>\n"
						+ "<buchungsDatum>2014-09-22+02:00</buchungsDatum>\n"
						+ "<belegDatum>2014-09-10+02:00</belegDatum>\n"
						+ "<freigabeVon>Hrn. Hofrat</freigabeVon>\n"
						+ "<bearbeitendeStelle>HST_WIEN</bearbeitendeStelle>\n"
					+ "</testStepDTO>\n"
				+ "</testStep101>\n";
		
		LinkMessage linkMessage;
		linkMessage = testStep.getMessage();
		assertEquals("LAVA", linkMessage.getApplikation());
		assertEquals("BW", linkMessage.getFunktion());
		assertEquals("TestStep101", linkMessage.getAktion());
		assertEquals("wojhals879dkfj121", linkMessage.getCorrelationId());
		assertEquals(vergleichsInhalt, linkMessage.getInhalt());
	}
	
}
