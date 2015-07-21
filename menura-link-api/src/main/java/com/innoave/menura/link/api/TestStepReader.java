/**
 * @(#) $Id: TestStepReader.java,v 1.0 20.02.2015 15:52:18 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: TestStepReader.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public interface TestStepReader {
	
	TestStep readTestStep(File file) throws IOException, ParseException;
	
	TestStep readTestStep(InputStream in) throws IOException, ParseException;
	
	TestStep readTestStep(String text) throws IOException, ParseException;

	TestStep readTestStep(Reader reader) throws IOException, ParseException;
	
}
