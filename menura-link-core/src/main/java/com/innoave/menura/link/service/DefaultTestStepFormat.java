/**
 * @(#) $Id: TestCaseFormat.java 9275 2014-10-09 13:04:20Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: TestCaseFormat.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

/**
 *
 *
 * @version $Revision: 9275 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class DefaultTestStepFormat {
	
	public static enum Section {
		TEST_SETUP,
		MESSAGE_HEADER,
		MESSAGE_BODY;
	}
	
	public static class HeaderParam {
		public static final String APPLICATION = "applikation";
		public static final String FUNCTION = "funktion";
		public static final String ACTION = "aktion";
		public static final String CORRELATION_ID = "correlationId";
	}
	
	public static class TestSetupParam {
		public static final String TEST_STEP_NAME = "test.step.name";
		public static final String SYSTEM_ADAPTER = "system.adapter";
		public static final String MESSAGE_HANDLER = "message.handler";
	}

}
