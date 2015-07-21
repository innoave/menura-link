/**
 * @(#) $Id: TestSystemAdapter.java,v 1.0 23.02.2015 14:12:20 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: TestSystemAdapter.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.test.adapter;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.SystemAdapter;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class TestSystemAdapter extends AbstractLinkComponent
		implements SystemAdapter {
	
	
	@Override
	public void startup(Configuration configuration) throws LinkException {
	}
	
	@Override
	public void shutdown() throws LinkException {
	}

}
