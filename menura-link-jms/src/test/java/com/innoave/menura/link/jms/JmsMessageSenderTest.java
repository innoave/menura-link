/**
 * @(#) $Id: JmsMessageSenderTest.java 10083 2014-12-04 14:43:28Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: JmsMessageSenderTest.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.jms;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Before;
import org.junit.Test;

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageSender;
import com.innoave.menura.link.jms.JmsMessageSender;
import com.innoave.menura.link.jms.MessageAttribute;

/**
 *
 *
 * @version $Revision: 10083 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class JmsMessageSenderTest {

	private LinkMessageSender sender;
	
	private QueueConnectionFactory queueConnectionFactory;
	
	private String requestQueueName = "sut.q.in";
	private String requestQueueUser = "request.user";
	private String requestQueuePass = "requestuser1";
	
	private String responseQueueName = "linkadapter.q.in";
	private String responseQueueUser = "response.user";
	private String responseQueuePass = "responseuser1";
	
	private QueueConnection queueConnection;
	private Queue requestQueue;
	private Queue responseQueue;
	private QueueSession queueSession;
	private QueueSender requestQueueSender;
	private TextMessage jmsMsg;
	
	
	@Before
	public void setUp() throws Exception {
		queueConnectionFactory = mock(QueueConnectionFactory.class);
		queueConnection = mock(QueueConnection.class);
		requestQueue = mock(Queue.class);
		responseQueue = mock(Queue.class);
		queueSession = mock(QueueSession.class);
		requestQueueSender = mock(QueueSender.class);
		jmsMsg = mock(TextMessage.class);
		
		when(queueConnectionFactory.createQueueConnection(eq(requestQueueUser), eq(requestQueuePass)))
				.thenReturn(queueConnection);
		when(queueConnection.createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE)))
				.thenReturn(queueSession);
		when(queueSession.createQueue(eq(requestQueueName))).thenReturn(requestQueue);
		when(queueSession.createQueue(eq(responseQueueName))).thenReturn(responseQueue);
		when(queueSession.createSender(eq(requestQueue))).thenReturn(requestQueueSender);
		
		sender = new JmsMessageSender(queueConnectionFactory,
				requestQueueName, requestQueueUser, requestQueuePass,
				responseQueueName);
	}
	
	
	@Test
	public void testSendTextMessage() throws Exception {
		String applikation = "menuralink", funktion = "test";
		String aktion = "HelloWorldV1";
		String correlationId = "476ab2383se529x";
		String inhalt = "Hallo Spencer!";
		LinkMessage linkMsg = new LinkMessage(applikation, funktion, aktion, correlationId, inhalt);
		
		when(queueSession.createTextMessage(eq(inhalt))).thenReturn(jmsMsg);
		
		sender.sendMessage(linkMsg);
		
		verify(jmsMsg).setJMSCorrelationID(eq(correlationId));
		verify(jmsMsg).setJMSReplyTo(eq(responseQueue));
		verify(jmsMsg).setJMSType(eq(aktion));
		verify(jmsMsg).setStringProperty(eq(MessageAttribute.NB_ACTION), eq(aktion));
		verify(jmsMsg).setStringProperty(eq(MessageAttribute.NB_FUNCTION), eq(funktion));
		verify(jmsMsg).setStringProperty(eq(MessageAttribute.NB_APPLICATION), eq(applikation));
		
		verify(requestQueueSender).send(eq(jmsMsg));
	}
	
}
