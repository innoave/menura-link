/**
 * @(#) $Id: LoggerFactory.java,v 1.0 26.02.2015 13:49:17 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: LoggerFactory.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import java.util.HashMap;
import java.util.Map;

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
public class ObjectLoggerFactory {
	
	private static final Map<Class<?>, Class<? extends ObjectLogger<?>>> objectLoggerTypeMap =
			new HashMap<Class<?>, Class<? extends ObjectLogger<?>>>();
	
	static {
		objectLoggerTypeMap.put(LinkMessage.class, LinkMessageLogger.class);
	}
	

	public static final <T> ObjectLogger<T> getObjectLogger(final Class<T> objectType) {
		final Class<? extends ObjectLogger<?>> loggerClazz = objectLoggerTypeMap.get(objectType);
		if (loggerClazz == null) {
			throw new IllegalStateException("No ObjectLogger class for object type " + objectType.getName() + " found");
		}
		try {
			@SuppressWarnings("unchecked")
			final ObjectLogger<T> instance = (ObjectLogger<T>) loggerClazz.newInstance();
			return instance;
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
