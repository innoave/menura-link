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
