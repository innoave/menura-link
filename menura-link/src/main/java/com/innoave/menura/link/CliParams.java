/**
 * @(#) $Id: CliParams.java,v 1.0 20.02.2015 19:22:45 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Changes:
 *
 * $Log: CliParams.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class CliParams {

	@Option(name = "-steps", usage = "Ein oder mehrere TestStep-Dateien", required = true, metaVar="STEPFILE"
			, handler=StringArrayOptionHandler.class)
	private String[] steps;

	
	public String[] getSteps() {
		return steps;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}
	
}
