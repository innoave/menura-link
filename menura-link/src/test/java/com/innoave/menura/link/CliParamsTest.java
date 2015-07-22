/**
 * @(#) $Id: CliParamsTest.java,v 1.0 23.02.2015 09:25:05 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: CliParamsTest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.innoave.menura.link.CliParams;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class CliParamsTest {

	private CliParams cliParams;
	
	private CmdLineParser cmdLineParser;
	
	
	@Before
	public void setUp() {
		cliParams = new CliParams();
		cmdLineParser = new CmdLineParser(cliParams);
	}
	
	
	@Test
	public void testPrintUsage() {
		StringWriter sw = new StringWriter();
		
		cmdLineParser.printUsage(sw, null);
		
		assertEquals(" -steps STEPFILE : Ein oder mehrere TestStep-Dateien\r\n", sw.toString());
	}
	
	@Test
	public void testParseArgument_NoArgumentGiven() {
		String[] args = new String[] {
		};
		try {
			cmdLineParser.parseArgument(args);
			fail("CmdLineException expected");
		} catch (CmdLineException e) {
			assertEquals("Option \"-steps\" is required", e.getMessage());
		}
	}
	
	@Test
	public void testParseArgument_StepsArg_NoFilenames() throws CmdLineException {
		String[] args = new String[] {
				"-steps"
		};
		cmdLineParser.parseArgument(args);
		
		assertNull(cliParams.getSteps());
	}
	
	@Test
	public void testParseArgument_StepsArg_OneFilename() throws CmdLineException {
		String[] args = new String[] {
				"-steps",
				"/home/link/test/steps/step1.lts"
		};
		cmdLineParser.parseArgument(args);
		
		assertEquals(1, cliParams.getSteps().length);
		assertEquals("/home/link/test/steps/step1.lts", cliParams.getSteps()[0]);
	}
	
	@Test
	public void testParseArgument_StepsArg_ThreeFilenames() throws CmdLineException {
		String[] args = new String[] {
				"-steps",
				"/home/link/test/steps/step1.lts",
				"/home/link/smoke/test/requestA.lts",
				"/home/link/smoke/test/answerA.lts"
		};
		cmdLineParser.parseArgument(args);
		
		assertEquals(3, cliParams.getSteps().length);
		assertEquals("/home/link/test/steps/step1.lts", cliParams.getSteps()[0]);
		assertEquals("/home/link/smoke/test/requestA.lts", cliParams.getSteps()[1]);
		assertEquals("/home/link/smoke/test/answerA.lts", cliParams.getSteps()[2]);
	}
	
}
