/**
 * @(#) $Id: AssertingXpathMessageHandler.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: AssertingXpathMessageHandler.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.assertion;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.AssertResult;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.ObjectLogger;
import com.innoave.menura.link.api.Test;
import com.innoave.menura.link.api.TestStep;
import com.innoave.menura.link.expressions.LinkExpressionEvaluator;
import com.innoave.menura.link.service.ObjectLoggerFactory;
import com.innoave.menura.link.service.SessionContext;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
@Test(FunctionalType.VERIFICATION)
public class AssertingXpathMessageHandler extends AbstractLinkComponent
		implements LinkMessageHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ObjectLogger<LinkMessage> linkMessageLogger = ObjectLoggerFactory.getObjectLogger(LinkMessage.class);
	
	private SessionContext context = SessionContext.instance();
	
	private LinkMessage expected;
	
	private LinkExpressionEvaluator expressionEvaluator = new LinkExpressionEvaluator();
	
	
	public AssertingXpathMessageHandler() {
	}
	
	public AssertingXpathMessageHandler(
			final String name,
			final LinkMessage expected
			) {
		super(name);
		this.expected = expected;
	}

	public LinkMessage getExpected() {
		return expected;
	}

	public void setExpected(LinkMessage expected) {
		this.expected = expected;
	}

	public LinkExpressionEvaluator getExpressionEvaluator() {
		return expressionEvaluator;
	}

	public void setExpressionEvaluator(LinkExpressionEvaluator expressionEvaluator) {
		this.expressionEvaluator = expressionEvaluator;
	}

	@Override
	public void startup(final Configuration configuration, final TestStep testStep) {
		log.debug("Initialisiere {} fuer Test Step {}...", getClass().getSimpleName(), testStep.getName());
		expected = testStep.getMessage();
		log.debug("...Initialisieren von {} beendet", getName());
	}

	@Override
	public void shutdown() {
		log.debug("Shutting down {}...", getName());
		log.debug("...Shutdown von {} beendet", getName());
	}
	
	@Override
	public void handleMessage(final LinkMessage actual) {
		if (expected == null && actual == null) {
			log.warn("Erwartete LinkMessage und aktuelle LinkMesssage sind beide NULL");
			return;
		}
		if (expected == null) {
			log.warn("Erwartete LinkMessage ist NULL");
			return;
		}
		if (actual == null) {
			log.warn("Aktuelle LinkMessage ist NULL");
			return;
		}
		final Properties expectedExpr = new Properties();
		try {
			expectedExpr.load(new StringReader(expected.getInhalt()));
		} catch (IOException e) {
			log.error("Kann Inhalt aus Step-Datei nicht in Properties laden", e);
			return;
		}
		final Document actualDom;
		try {
			 actualDom = readDocumentFromString(actual.getInhalt());
		} catch (Exception e) {
			log.error("Kann Inhalt von Antwort-Meldung nicht in DOM laden. Inhalt: {}", actual.getInhalt());
			return;
		}
		for (final String xpath : expectedExpr.stringPropertyNames()) {
			final String expression = expectedExpr.getProperty(xpath);
			final AssertResult result;
			result = expressionEvaluator.evaluate(expression, xpath, actualDom);
			final Object expectedValue = result.getExpectedValue();
			final String attributeName = expectedValue == null ? null : String.valueOf(expectedValue);
			context.putAttribute(attributeName, result.getActualValue());
			logAssertResults(new AssertResult[] { result });
		}
		log.info("Erwarteter Inhalt: {}", linkMessageLogger.buildLogMessage(expected));
	}
	
	protected void logAssertResults(final AssertResult[] assertResults) {
		for (final AssertResult result : assertResults) {
			switch (result.getResultCode()) {
			case OK:
				log.info(result.getDescription());
				break;
			case ERROR:
				log.warn(result.getDescription());
				break;
			case ATTRIBUTE:
				log.info(result.getDescription());
				break;
			}
		}
	}
	
	private Document readDocumentFromString(final String xmlContent) throws SAXException, IOException, ParserConfigurationException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final StringReader stringReader = new StringReader(xmlContent);
		try {
			final Document doc;
			doc = db.parse(new InputSource(stringReader));
			return doc;
		} finally {
			stringReader.close();
		}
	}

}
