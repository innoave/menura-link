/**
 * @(#) $Id: JmsConfigKey.java,v 1.0 18.02.2015 11:44:57 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: JmsConfigKey.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.jms;

import com.innoave.menura.link.api.Configuration;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public enum JmsConfigKey implements Configuration.Key {

//	INITIAL_JNDI_CONTEXT_FACTORY ("initial.jndi.context.factory", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"),	
//	QUEUE_CONNECTION_FACTORY_JNDI_NAME ("queue.connection.factory.jndi.name", "QueueConnectionFactory"),
	
	QUEUE_CONNECTION_FACTORY ("queue.connection.factory.name", "org.apache.activemq.ActiveMQConnectionFactory"),

	JMS_PROVIDER_URL ("jms.provider.url", "tcp://localhost:61616"),
	
	REQUEST_QUEUE_PHYSICAL_NAME ("request.queue.physical.name", "sut.q.in"),
	REQUEST_QUEUE_JNDI_NAME ("request.queue.jndi.name", "queue.sut.q.in"),
	REQUEST_QUEUE_USERNAME ("request.queue.username", "menura"),
	REQUEST_QUEUE_PASSWORD ("request.queue.password", "menura1"),
	
	RESPONSE_QUEUE_PHYSICAL_NAME ("response.queue.physical.name", "linkadapter.q.in"),
	RESPONSE_QUEUE_JNDI_NAME ("response.queue.jndi.name", "linkadapter.lava.q.in"),
	RESPONSE_QUEUE_USERNAME ("response.queue.username", "menura"),
	RESPONSE_QUEUE_PASSWORD ("response.queue.password", "menura1");
	
	
	private final String propertyKey;
	private final String defaultValue;
	
	JmsConfigKey(
			final String propertyKey,
			final String defaultValue
			) {
		this.propertyKey = propertyKey;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getPropertyKey() {
		return propertyKey;
	}
	
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	
}
