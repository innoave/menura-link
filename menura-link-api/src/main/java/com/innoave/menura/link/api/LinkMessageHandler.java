/**
 * @(#) $Id: LinkMessageHandler.java 9291 2014-10-09 16:25:41Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: LinkMessageHandler.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;


/**
 *
 *
 * @version $Revision: 9291 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public interface LinkMessageHandler extends LinkComponent {
	
	void startup(Configuration configuration, TestStep testStep) throws LinkException;
	
	void shutdown() throws LinkException;
	
	void handleMessage(LinkMessage linkMessage);
	
}
