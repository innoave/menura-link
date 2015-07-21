/**
 * @(#) $Id: DefaultTestStepExecutor.java,v 1.0 20.02.2015 16:48:16 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: DefaultTestStepExecutor.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.api.TestStepExecutor;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class DefaultTestStepExecutor implements TestStepExecutor {
	
	private final String name;
	
	private final LinkMessage message;
	
	private final LinkMessageHandler messageHandler; 
	
	private final SystemAdapter systemAdapter;
	
	
	public DefaultTestStepExecutor(
			final String name,
			final LinkMessage message,
			final LinkMessageHandler messageHandler,
			final SystemAdapter systemAdapter
			) {
		this.name = name;
		this.message = message;
		this.messageHandler = messageHandler;
		this.systemAdapter = systemAdapter;
	}
	
	public String getName() {
		return name;
	}
	
	public LinkMessage getMessage() {
		return message;
	}
	
	public LinkMessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	public SystemAdapter getSystemAdapter() {
		return systemAdapter;
	}
	
	@Override
	public void execute() {
		messageHandler.handleMessage(message);
	}
	
}
