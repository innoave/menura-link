/**
 * @(#) $Id: TestStep.java,v 1.0 18.02.2015 18:37:27 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: TestStep.java,v $
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
public class TestStep {
	
	private String name;
	
	private FunctionalType type;

	private Class<SystemAdapter> systemAdapter;
	
	private Class<LinkMessageHandler> messageHandler;
	
	private LinkMessage message;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FunctionalType getType() {
		return type;
	}

	public void setType(FunctionalType type) {
		this.type = type;
	}

	public Class<SystemAdapter> getSystemAdapter() {
		return systemAdapter;
	}

	public void setSystemAdapter(Class<SystemAdapter> systemAdapter) {
		this.systemAdapter = systemAdapter;
	}

	public Class<LinkMessageHandler> getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(Class<LinkMessageHandler> messageHandler) {
		this.messageHandler = messageHandler;
	}

	public LinkMessage getMessage() {
		return message;
	}

	public void setMessage(LinkMessage message) {
		this.message = message;
	}
	
}
