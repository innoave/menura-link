/**
 * @(#) $Id: LinkMessage.java 9275 2014-10-09 13:04:20Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: LinkMessage.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @version $Revision: 9275 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class LinkMessage {

	protected String applikation;
	
	protected String funktion;
	
	protected String aktion;

	protected String correlationId;
	
	protected String inhalt;
	
	
	public LinkMessage() {
	}
	
	public LinkMessage(
			final String applikation,
			final String funktion,
			final String aktion,
			final String correlationId,
			final String inhalt
			) {
		this.applikation = applikation;
		this.funktion = funktion;
		this.aktion = aktion;
		this.correlationId = correlationId;
		this.inhalt = inhalt;
	}

	
	public String getApplikation() {
		return applikation;
	}

	public void setApplikation(String applikation) {
		this.applikation = applikation;
	}

	public String getFunktion() {
		return funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	public String getAktion() {
		return aktion;
	}

	public void setAktion(String aktion) {
		this.aktion = aktion;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	
}
