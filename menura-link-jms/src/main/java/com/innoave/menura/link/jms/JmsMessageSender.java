/**
 *  Copyright (c) 2014 Innoave.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.innoave.menura.link.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageSender;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class JmsMessageSender extends AbstractLinkComponent
		implements LinkMessageSender {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private QueueConnectionFactory queueConnectionFactory;
	
	private String requestQueueName;
	
	private String requestQueueUsername;
	
	private String requestQueuePassword;
	
	private String responseQueueName;

	
	public JmsMessageSender() {
	}
	
	public JmsMessageSender(
			final QueueConnectionFactory queueConnectionFactory
			) {
		this.queueConnectionFactory = queueConnectionFactory;
	}
	
	public JmsMessageSender(
			final QueueConnectionFactory queueConnectionFactory,
			final String requestQueueName,
			final String responseQueueName
			) {
		this.queueConnectionFactory = queueConnectionFactory;
	}
	
	public JmsMessageSender(
			final QueueConnectionFactory queueConnectionFactory,
			final String requestQueueName,
			final String requestQueueUsername,
			final String requestQueuePassword,
			final String responseQueueName
			) {
		this.queueConnectionFactory = queueConnectionFactory;		
		this.requestQueueName = requestQueueName;
		this.requestQueueUsername = requestQueueUsername;
		this.requestQueuePassword = requestQueuePassword;
		this.responseQueueName = responseQueueName;
	}

	public QueueConnectionFactory getQueueConnectionFactory() {
		return queueConnectionFactory;
	}

	public void setQueueConnectionFactory(QueueConnectionFactory queueConnectionFactory) {
		this.queueConnectionFactory = queueConnectionFactory;
	}

	public String getRequestQueueName() {
		return requestQueueName;
	}

	public void setRequestQueueName(String requestQueueName) {
		this.requestQueueName = requestQueueName;
	}

	public String getRequestQueueUsername() {
		return requestQueueUsername;
	}

	public void setRequestQueueUsername(String requestQueueUsername) {
		this.requestQueueUsername = requestQueueUsername;
	}

	public String getRequestQueuePassword() {
		return requestQueuePassword;
	}

	public void setRequestQueuePassword(String requestQueuePassword) {
		this.requestQueuePassword = requestQueuePassword;
	}

	public String getResponseQueueName() {
		return responseQueueName;
	}

	public void setResponseQueueName(String responseQueueName) {
		this.responseQueueName = responseQueueName;
	}
	
	private void initialize(final Configuration configuration) {
		if (requestQueueName == null) {
			requestQueueName = configuration.get(JmsConfigKey.REQUEST_QUEUE_PHYSICAL_NAME);
		}
		if (requestQueueUsername == null) {
			requestQueueUsername = configuration.get(JmsConfigKey.REQUEST_QUEUE_USERNAME);
		}
		if (requestQueuePassword == null) {
			requestQueuePassword = configuration.get(JmsConfigKey.REQUEST_QUEUE_PASSWORD);
		}
		if (responseQueueName == null) {
			responseQueueName = configuration.get(JmsConfigKey.RESPONSE_QUEUE_PHYSICAL_NAME);
		}
	}
	
	@Override
	public void startup(final Configuration configuration) {
		log.debug("Initialisiere {}...", getName());
		initialize(configuration);
		log.debug("...Initialisieren von {} beendet", getName());
	}
	
	@Override
	public void shutdown() {
		log.debug("Shutting down {}...", getName());
		log.debug("...Shutdown von {} beendet", getName());
	}
	
	@Override
	public String sendMessage(final LinkMessage linkMsg) throws LinkException {
		log.info("Sende Meldung: aktion={}, correlationId={}, funktion={}, applikation={}",
				linkMsg.getAktion(),
				linkMsg.getCorrelationId(),
				linkMsg.getFunktion(),
				linkMsg.getApplikation()
				);
		log.debug("  Inhalt: {}", linkMsg.getInhalt());
		String messageId;
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;
		try {
			if (requestQueueUsername != null) {
				queueConnection = queueConnectionFactory.createQueueConnection(
						requestQueueUsername, requestQueuePassword);
			} else {
				queueConnection = queueConnectionFactory.createQueueConnection();
			}
			queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			final Queue requestQueue = queueSession.createQueue(requestQueueName);
			final Queue responseQueue = queueSession.createQueue(responseQueueName);
			queueSender = queueSession.createSender(requestQueue);
			
			final TextMessage jmsMsg = queueSession.createTextMessage(linkMsg.getInhalt());
			jmsMsg.setJMSCorrelationID(linkMsg.getCorrelationId());
			jmsMsg.setJMSReplyTo(responseQueue);
			jmsMsg.setJMSType(linkMsg.getAktion());
			jmsMsg.setStringProperty(MessageAttribute.NB_ACTION, linkMsg.getAktion());
			jmsMsg.setStringProperty(MessageAttribute.NB_FUNCTION, linkMsg.getFunktion());
			jmsMsg.setStringProperty(MessageAttribute.NB_APPLICATION, linkMsg.getApplikation());
			
			queueSender.send(jmsMsg);
			
			messageId = jmsMsg.getJMSMessageID();
			log.info("...Meldung gesendet. JMS MessageID={}", messageId);
			return messageId;
			
		} catch (JMSException e) {
			throw new LinkException("Fehler beim Senden der LinkMessage " + linkMsg.getAktion()
					+ " an " + linkMsg.getFunktion() + "@" + linkMsg.getApplikation()
					+ " (correlation-ID=" + linkMsg.getCorrelationId() + ")",
					e);
		} finally {
			if (queueSender != null) {
				try {
					queueSender.close();
				} catch (JMSException e) {
					throw new LinkException("Fehler beim Schliessen des Queue-Senders", e);
				}
			}
			if (queueSession != null) {
				try {
					queueSession.close();
				} catch (JMSException e) {
					throw new LinkException("Fehler beim Schliessen der Queue-Session", e);
				}
			}
		}
	}
	
}
