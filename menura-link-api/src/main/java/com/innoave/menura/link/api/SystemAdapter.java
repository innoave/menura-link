/**
 * @(#) $Id: InterfaceAdapter.java,v 1.0 18.02.2015 10:20:41 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: InterfaceAdapter.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 * <p>
 * Adapter to the system under test (SUT).
 * </p>
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public interface SystemAdapter extends LinkComponent {
	
	void startup(Configuration configuration) throws LinkException;
	
	void shutdown() throws LinkException;
	
}
