/**
 * @(#) $Id: LinkMessageReceiver.java 9314 2014-10-13 16:41:20Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: LinkMessageReceiver.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @version $Revision: 9314 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public interface LinkMessageReceiver<T> extends LinkComponent {
	
	void startup(Configuration configuration) throws LinkException;
	
	void shutdown() throws LinkException;

	void receiveMessage(T message) throws LinkException;
	
}
