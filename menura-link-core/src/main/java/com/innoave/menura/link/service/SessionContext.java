/**
 * @(#) $Id: SessionContext.java,v 1.0 07.07.2015 10:05:54 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: SessionContext.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class SessionContext {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	
	private static final ThreadLocal<SessionContext> instance = new ThreadLocal<SessionContext>() {
		@Override
		protected SessionContext initialValue() {
			return new SessionContext();
		}
	};
	
	public static final SessionContext instance() {
		return instance.get();
	}
	
	private SessionContext() {
	}
	
	public Object getAttribute(final String name) {
		return attributes.get(name);
	}
	
	public void putAttribute(final String name, final Object value) {
		attributes.put(name, value);
	}
	
}
