/**
 * @(#) $Id: LinkMessageLogger.java,v 1.0 26.02.2015 11:58:40 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: LinkMessageLogger.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.ObjectLogger;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class LinkMessageLogger implements ObjectLogger<LinkMessage> {

	@Override
	public String buildLogMessage(final LinkMessage value) {
		final StringBuilder sb = new StringBuilder();
		sb.append("aktion=");
		sb.append(value.getAktion());
		sb.append(", correlationId=");
		sb.append(value.getCorrelationId());
		sb.append(", funktion=");
		sb.append(value.getFunktion());
		sb.append(", applikation=");
		sb.append(value.getApplikation());
		sb.append("\n   inhalt: ");
		sb.append(value.getInhalt());
		return sb.toString();
	}
	
}
