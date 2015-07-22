/**
 * @(#) $Id: TestRequestHandler.java,v 1.0 23.02.2015 14:27:09 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: TestRequestHandler.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.test.handler;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.Test;
import com.innoave.menura.link.api.TestStep;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
@Test(FunctionalType.ACTION)
public class TestRequestHandler extends AbstractLinkComponent
		implements LinkMessageHandler {


	@Override
	public void startup(Configuration configuration, TestStep testStep) throws LinkException {
		
	}

	@Override
	public void shutdown() throws LinkException {
		
	}

	@Override
	public void handleMessage(LinkMessage linkMessage) {
		
	}

}
