/**
 * @(#) $Id: LinkException.java,v 1.0 18.02.2015 11:18:42 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: LinkException.java,v $
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
public class LinkException extends Exception {

	private static final long serialVersionUID = 1L;


	public LinkException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public LinkException(final String message) {
		super(message);
	}

	public LinkException(final Throwable cause) {
		super(cause);
	}

}
