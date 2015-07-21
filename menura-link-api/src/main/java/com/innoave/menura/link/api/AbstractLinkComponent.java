/**
 * @(#) $Id: AbstractLinkComponent.java,v 1.0 20.02.2015 11:00:03 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: AbstractLinkComponent.java,v $
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
public abstract class AbstractLinkComponent implements LinkComponent {
	
	private String name;

	
	public AbstractLinkComponent() {
	}
	
	public AbstractLinkComponent(final String name) {
		this.name = name;
	}

	@Override
	public final String getName() {
		return name;
	}
	
	@Override
	public final void setName(final String name) {
		this.name = name;
	}
	
}
