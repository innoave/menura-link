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
 * @author haraldmaida
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
		log.info("Erwarteter Inhalt: {}", linkMessageLogger.buildLogMessage(expected));
		for (final String xpath : expectedExpr.stringPropertyNames()) {
			final String expression = expectedExpr.getProperty(xpath);
			final AssertResult result;
			result = expressionEvaluator.evaluate(expression, xpath, actualDom);
			logAssertResults(result);
			switch (result.getResultCode()) {
			case ATTRIBUTE:
				final Object expectedValue = result.getExpectedValue();
				final String attributeName = expectedValue == null ? null : String.valueOf(expectedValue);
				context.putAttribute(attributeName, result.getActualValue());
				break;
			}
		}
	}
	
	protected void logAssertResults(final AssertResult... assertResults) {
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
