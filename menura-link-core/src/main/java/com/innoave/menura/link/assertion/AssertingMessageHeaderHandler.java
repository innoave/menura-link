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

import static com.innoave.menura.link.assertion.AssertingHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.AbstractLinkComponent;
import com.innoave.menura.link.api.AssertResult;
import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.Test;
import com.innoave.menura.link.api.TestStep;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
@Test(FunctionalType.VERIFICATION)
public class AssertingMessageHeaderHandler extends AbstractLinkComponent
		implements LinkMessageHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ResultDescriptionBuilder resultDescriptionBuilder = new ResultDescriptionBuilder();
	
	private LinkMessage expected;
	
	
	public AssertingMessageHeaderHandler() {
	}
	
	public AssertingMessageHeaderHandler(
			final String name,
			final LinkMessage expected
			) {
		super(name);
		this.expected = expected;
	}

	public final LinkMessage getExpected() {
		return expected;
	}

	public final void setExpected(LinkMessage expected) {
		this.expected = expected;
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
		final AssertResult[] assertResults;
		assertResults = assertHeaderAttributes(getExpected(), actual);
		logAssertResults(assertResults);
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
	
	protected AssertResult assertProperty(final String propertyName, final String expected, final String actual) {
		final AssertResult result;
		final AssertResult.ResultCode resultCode = assertEquals(expected, actual) ? 
				AssertResult.ResultCode.OK : AssertResult.ResultCode.ERROR;
		final String description = resultDescriptionBuilder.buildDescription(resultCode, propertyName, expected, actual);
		result = new AssertResult(resultCode, description, propertyName, expected, actual);
		return result;
	}
	
	protected AssertResult[] assertHeaderAttributes(final LinkMessage expected, LinkMessage actual) {
		final List<AssertResult> resultList = new ArrayList<AssertResult>();
		resultList.add(assertProperty("Applikation", expected.getApplikation(), actual.getApplikation()));
		resultList.add(assertProperty("Funktion", expected.getFunktion(), actual.getFunktion()));
		resultList.add(assertProperty("Aktion", expected.getAktion(), actual.getAktion()));
		resultList.add(assertProperty("CorrelationId", expected.getCorrelationId(), actual.getCorrelationId()));
		return resultList.toArray(new AssertResult[resultList.size()]);
	}

}
