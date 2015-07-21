/**
 * @(#) $Id: JmsMessageReceiver.java 9314 2014-10-13 16:41:20Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: JmsMessageReceiver.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.LinkMessageReceiver;

/**
 *
 *
 * @version $Revision: 9314 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class JmsMessageReceiver extends AbstractLinkComponent
		implements LinkMessageReceiver<Message>, MessageListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private QueueConnectionFactory queueConnectionFactory;
	
	private String responseQueueName;
	
	private String responseQueueUsername;
	
	private String responseQueuePassword;
	
	private LinkMessageHandler messageHandler; 
	
	private QueueSession queueSession;
	private QueueReceiver queueReceiver;
	private QueueConnection queueConnection;
	
	
	public JmsMessageReceiver() {
	}
	
	public JmsMessageReceiver(
			final QueueConnectionFactory queueConnectionFactory,
			final LinkMessageHandler messageHandler
			) {
		this.queueConnectionFactory = queueConnectionFactory;
		this.messageHandler = messageHandler;
	}
	
	public JmsMessageReceiver(
			final QueueConnectionFactory queueConnectionFactory,
			final String responseQueueName,
			final LinkMessageHandler messageHandler
			) {
		this.queueConnectionFactory = queueConnectionFactory;		
		this.responseQueueName = responseQueueName;
		this.messageHandler = messageHandler;
	}
	
	public JmsMessageReceiver(
			final QueueConnectionFactory queueConnectionFactory,
			final String responseQueueName,
			final String responseQueueUsername,
			final String responseQueuePassword,
			final LinkMessageHandler messageHandler
			) {
		this.queueConnectionFactory = queueConnectionFactory;		
		this.responseQueueName = responseQueueName;
		this.responseQueueUsername = responseQueueUsername;
		this.responseQueuePassword = responseQueuePassword;
		this.messageHandler = messageHandler;
	}

	public QueueConnectionFactory getQueueConnectionFactory() {
		return queueConnectionFactory;
	}

	public void setQueueConnectionFactory(QueueConnectionFactory queueConnectionFactory) {
		this.queueConnectionFactory = queueConnectionFactory;
	}

	public String getResponseQueueName() {
		return responseQueueName;
	}

	public void setResponseQueueName(String responseQueueName) {
		this.responseQueueName = responseQueueName;
	}

	public String getResponseQueueUsername() {
		return responseQueueUsername;
	}

	public void setResponseQueueUsername(String responseQueueUsername) {
		this.responseQueueUsername = responseQueueUsername;
	}

	public String getResponseQueuePassword() {
		return responseQueuePassword;
	}

	public void setResponseQueuePassword(String responseQueuePassword) {
		this.responseQueuePassword = responseQueuePassword;
	}
	
	private void initialize(final Configuration configuration) {
		if (responseQueueName == null) {
			responseQueueName = configuration.get(JmsConfigKey.RESPONSE_QUEUE_PHYSICAL_NAME);
		}
		if (responseQueueUsername == null) {
			responseQueueUsername = configuration.get(JmsConfigKey.RESPONSE_QUEUE_USERNAME);
		}
		if (responseQueuePassword == null) {
			responseQueuePassword = configuration.get(JmsConfigKey.RESPONSE_QUEUE_PASSWORD);
		}
	}
	
	@Override
	public void startup(final Configuration configuration) throws LinkException {
		log.debug("Initialisiere {}...", getName());
		initialize(configuration);
		try {
			if (responseQueueUsername != null) {
				queueConnection = queueConnectionFactory.createQueueConnection(
						responseQueueUsername, responseQueuePassword
						);
			} else {
				queueConnection = queueConnectionFactory.createQueueConnection();
			}
			queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueConnection.start();
			final Queue responseQueue = queueSession.createQueue(responseQueueName);
			queueReceiver = queueSession.createReceiver(responseQueue);
			queueReceiver.setMessageListener(this);
			
		} catch (JMSException e) {
			throw new LinkException("Fehler beim Starten des Queue-Receivers", e);
		}
		log.debug("...Initialisieren von {} beendet", getName());
	}
	
	@Override
	public void shutdown() throws LinkException {
		log.debug("Shutting down {}...", getName());
		if (queueConnection != null) {
			try {
				queueConnection.stop();
				queueConnection.close();
			} catch (JMSException e) {
				throw new LinkException("Fehler beim Beenden der Queue-Connection", e);
			}
		}
		if (queueReceiver != null) {
			try {
				queueReceiver.close();
			} catch (JMSException e) {
				throw new LinkException("Fehler beim Schliessen des Queue-Receivers", e);
			}
		}
		if (queueSession != null) {
			try {
				queueSession.close();
			} catch (JMSException e) {
				throw  new LinkException("Fehler beim Schliessen der Queue-Session", e);
			}
		}
		log.debug("...Shutdown von {} beendet", getName());
	}
	
	@Override
	public void onMessage(final Message jmsMsg) {
		receiveMessage(jmsMsg);
	}

	@Override
	public void receiveMessage(final Message jmsMsg) {
		if (!(jmsMsg instanceof TextMessage)) {
			log.debug("...bin nur an TextMessage interessiert!");
			return;
		}
		final TextMessage textMsg = (TextMessage) jmsMsg;
		try {
			final String messageId = textMsg.getJMSMessageID();
			final String jmsType = textMsg.getJMSType();
			final String correlationId = textMsg.getJMSCorrelationID();
			final String replyToQueueName = textMsg.getJMSReplyTo() == null ? null
					: ((Queue) textMsg.getJMSReplyTo()).getQueueName();
			final String application = textMsg.getStringProperty(MessageAttribute.NB_APPLICATION);
			final String function = textMsg.getStringProperty(MessageAttribute.NB_FUNCTION);
			final String action = textMsg.getStringProperty(MessageAttribute.NB_ACTION);
			final String text = textMsg.getText();
			log.info("Empfange JMS-Message: jmsMessageId={}, jmsType={}, jmsCorrelationID={}, jmsReplyTo={}" +
					", action={}, function={}, application={}",
					messageId, jmsType, correlationId, replyToQueueName,
					action, function, application
					);
			log.debug("  text={}", text);
			final LinkMessage linkMsg = new LinkMessage(application, function, action, correlationId, text);
			log.info("Behandle LinkMessage durch {}...", messageHandler.getName());
			messageHandler.handleMessage(linkMsg);
		} catch (JMSException e) {
			log.error("Fehler beim Lesen der empfangenen JMS-Message", e);
		}
	}
	
}
