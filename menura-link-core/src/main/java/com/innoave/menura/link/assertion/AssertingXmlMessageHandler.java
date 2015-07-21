/**
 * @(#) $Id: AssertingXmlMessageHandler.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: AssertingXmlMessageHandler.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.assertion;

import static com.innoave.menura.link.assertion.AssertingHelper.*;

import java.util.Collections;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.NodeDescriptor;
import org.custommonkey.xmlunit.NodeDetail;
import org.custommonkey.xmlunit.XMLUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.ObjectLogger;
import com.innoave.menura.link.api.Test;
import com.innoave.menura.link.api.TestStep;
import com.innoave.menura.link.service.ObjectLoggerFactory;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
@Test(FunctionalType.VERIFICATION)
public class AssertingXmlMessageHandler extends AbstractLinkComponent
		implements LinkMessageHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ObjectLogger<LinkMessage> linkMessageLogger = ObjectLoggerFactory.getObjectLogger(LinkMessage.class);
	
	private LinkMessage expected;
	
	private boolean doXmlDiff = true;
	private boolean ignoreWhitespace = true;
	private boolean ignoreComments = true;
	private boolean ignoreAttributeOrder = true;
	private boolean ignoreNamespacePrefix = true;

	
	public AssertingXmlMessageHandler() {
	}
	
	public AssertingXmlMessageHandler(
			final String name,
			final LinkMessage expected,
			final boolean doXmlDiff
			) {
		super(name);
		this.expected = expected;
		this.doXmlDiff = doXmlDiff;
	}

	public LinkMessage getExpected() {
		return expected;
	}

	public void setExpected(LinkMessage expected) {
		this.expected = expected;
	}

	public boolean isDoXmlDiff() {
		return doXmlDiff;
	}

	public void setDoXmlDiff(boolean doXmlDiff) {
		this.doXmlDiff = doXmlDiff;
	}

	public boolean isIgnoreWhitespace() {
		return ignoreWhitespace;
	}

	public void setIgnoreWhitespace(boolean ignoreWhitespace) {
		this.ignoreWhitespace = ignoreWhitespace;
	}

	public boolean isIgnoreComments() {
		return ignoreComments;
	}

	public void setIgnoreComments(boolean ignoreComments) {
		this.ignoreComments = ignoreComments;
	}

	public boolean isIgnoreAttributeOrder() {
		return ignoreAttributeOrder;
	}

	public void setIgnoreAttributeOrder(boolean ignoreAttributeOrder) {
		this.ignoreAttributeOrder = ignoreAttributeOrder;
	}

	public boolean isIgnoreNamespacePrefix() {
		return ignoreNamespacePrefix;
	}

	public void setIgnoreNamespacePrefix(boolean ignoreNamespacePrefix) {
		this.ignoreNamespacePrefix = ignoreNamespacePrefix;
	}

	@Override
	public void startup(final Configuration configuration, final TestStep testStep) {
		log.debug("Initialisiere {}...", getName());
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
		boolean diffFound = false;
		if (doXmlDiff) {
			initXmlDiff();
			final List<Difference> diffList = findAllDifferenciesInXml(expected.getInhalt(), actual.getInhalt());
			if (!diffList.isEmpty()) {
				final StringBuilder sb = new StringBuilder();
				sb.append("Fehler. Inhalt unterschiedlich:");
				int unignoreableDifferences = 0;
				for (final Difference diff : diffList) {
					if (ignoreNamespacePrefix && "namespace prefix".equals(diff.getDescription())) {
						continue;
					}
					unignoreableDifferences++;
					sb.append("\n   ").append(diff.getDescription()).append(":");
					sb.append(" erwartet=").append(formatLogEntry(diff.getControlNodeDetail()));
					sb.append(" <> aktuell=").append(formatLogEntry(diff.getTestNodeDetail()));
					sb.append(",");
				}
				int lastComma = sb.lastIndexOf(",");
				if (lastComma > -1) {
					sb.setCharAt(lastComma, ';');
				}
				if (unignoreableDifferences > 0) {
					diffFound = true;
					log.warn(sb.toString());
				}
				log.info("Erwarteter Inhalt: {}", linkMessageLogger.buildLogMessage(expected));
			}
		} else {
			if (!assertEquals(expected.getInhalt(), actual.getInhalt())) {
				diffFound = true;
				log.warn("Fehler. Inhalt unterschiedlich: erwartet={} <> aktuell={}",
						expected.getInhalt(), actual.getInhalt());
			}
		}
		if (!diffFound) {
			log.warn("OK. Erwartete Meldung und erhaltene Meldung sind identisch.");
		}
	}
	
	private String formatLogEntry(final NodeDetail nodeDetail) {
		if (nodeDetail == null) {
			return "";
		}
		final StringBuffer sb = new StringBuffer();
		NodeDescriptor.appendNodeDetail(sb, nodeDetail);
		sb.append(nodeDetail.getValue());
		return sb.toString();
	}
	
	private void initXmlDiff() {
		XMLUnit.setIgnoreWhitespace(ignoreWhitespace);
		XMLUnit.setIgnoreComments(ignoreComments);
		XMLUnit.setIgnoreAttributeOrder(ignoreAttributeOrder);
	}
	
	protected boolean assertXmlEquals(final String expected, final String actual) {
		if (expected == null) {
			if (actual != null) {
				return false;
			}
		} else {
			if (actual == null) {
				return false;
			}
		}
		Diff diff;
		try {
			diff = new Diff(expected, actual);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		final boolean identical = diff.identical();
		return identical;
	}
	
	protected List<Difference> findAllDifferenciesInXml(final String expected, final String actual) {
		if (expected == null) {
			if (actual != null) {
				return Collections.emptyList();
			}
		} else {
			if (actual == null) {
				return Collections.emptyList();
			}
		}
		Diff diff;
		try {
			diff = new Diff(expected, actual);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		final DetailedDiff dDiff = new DetailedDiff(diff);
		@SuppressWarnings("unchecked")
		final List<Difference> differencies = dDiff.getAllDifferences();
		return differencies;
	}

}
