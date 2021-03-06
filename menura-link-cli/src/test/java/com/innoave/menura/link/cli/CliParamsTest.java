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
package com.innoave.menura.link.cli;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.innoave.menura.link.cli.CliParams;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class CliParamsTest {
	
	private static String nl;

	private CliParams cliParams;
	
	private CmdLineParser cmdLineParser;
	
	
	@BeforeClass
	public static void setupOnce() {
		nl = System.getProperty("line.separator");
	}
	
	@Before
	public void setUp() {
		cliParams = new CliParams();
		cmdLineParser = new CmdLineParser(cliParams);
	}
	
	
	@Test
	public void testPrintUsage() {
		StringWriter sw = new StringWriter();
		
		cmdLineParser.printUsage(sw, null);
		
		assertEquals(" -steps STEPFILE : Ein oder mehrere TestStep-Dateien" + nl, sw.toString());
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
