/**
 * @(#) $Id: Configuration.java,v 1.0 18.02.2015 15:49:45 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: Configuration.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public interface Configuration {
	
	public interface Key {
		String getPropertyKey();
		String getDefaultValue();
	}

	String get(Key key);
	
}
