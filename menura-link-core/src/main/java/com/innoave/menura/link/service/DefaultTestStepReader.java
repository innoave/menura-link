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
package com.innoave.menura.link.service;

import static com.innoave.menura.link.service.DefaultTestStepFormat.HeaderParam;
import static com.innoave.menura.link.service.DefaultTestStepFormat.Section;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.ParseException;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.api.TestAnnotationProcessor;
import com.innoave.menura.link.api.TestStep;
import com.innoave.menura.link.api.TestStepReader;
import com.innoave.menura.link.service.DefaultTestStepFormat.TestSetupParam;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class DefaultTestStepReader implements TestStepReader {

	
	@Override
	public TestStep readTestStep(final File file) throws IOException, ParseException {
		final InputStream in = new FileInputStream(file);
		try {
			return readTestStep(in);
		} finally {
			in.close();
		}
	}
	
	@Override
	public TestStep readTestStep(final String text) throws IOException, ParseException {
		final Reader reader = new StringReader(text);
		try {
			return readTestStep(reader);
		} finally {
			reader.close();
		}
	}
	
	@Override
	public TestStep readTestStep(final InputStream in) throws IOException, ParseException {
		final Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readTestStep(reader);
		} finally {
			reader.close();
		}
	}
	
	@Override
	public TestStep readTestStep(final Reader reader) throws IOException, ParseException {
		final BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			final Map<Section, String> sections;
			sections = readFileSections(bufferedReader);
			final String testSetup = sections.get(Section.TEST_SETUP);
			if (testSetup == null) {
				throw new IOException("No Test-Setup-Section found in testcase");
			}
			final Properties testSetupParams = parsePropertiesSection(testSetup);
			final String testStepName = testSetupParams.getProperty(TestSetupParam.TEST_STEP_NAME);
			final LinkMessage linkMessage = parseLinkMessage(
					sections.get(Section.MESSAGE_HEADER), sections.get(Section.MESSAGE_BODY));
			final Class<SystemAdapter> systemAdapterClazz = parseClassProperty(
					SystemAdapter.class, testSetupParams.getProperty(TestSetupParam.SYSTEM_ADAPTER));
			final Class<LinkMessageHandler> handlerClazz = parseClassProperty(
					LinkMessageHandler.class, testSetupParams.getProperty(TestSetupParam.MESSAGE_HANDLER));
			final TestStep testStep = new TestStep();
			testStep.setName(testStepName);
			if (handlerClazz != null) {
				final FunctionalType testStepType;
				testStepType = new TestAnnotationProcessor().determineFunctionalType(handlerClazz);
				testStep.setType(testStepType);
				testStep.setMessageHandler(handlerClazz);
			}
			testStep.setMessage(linkMessage);
			testStep.setSystemAdapter(systemAdapterClazz);
			return testStep;
		} finally {
			bufferedReader.close();
		}
	}
	
	protected Map<Section, String> readFileSections(final BufferedReader reader) throws IOException {
		final Map<Section, StringBuilder> sectionMap = 
				new EnumMap<Section, StringBuilder>(Section.class);
		Section section = null;
		StringBuilder content = null;
		String line = reader.readLine();
		while (line != null) {
			if (line.startsWith("---")) {
				if (line.indexOf("TEST-SETUP") >= 0) {
					section = Section.TEST_SETUP;
				} else  if (line.indexOf("MESSAGE-HEADER") >= 0) {
					section = Section.MESSAGE_HEADER;
				} else if (line.indexOf("MESSAGE-BODY") >= 0) {
					section = Section.MESSAGE_BODY;
				} else {
					throw new IOException("Unbekannte Section in Link-Message-Datei");
				}
				content = sectionMap.get(section);
				if (content == null) {
					content = new StringBuilder();
					sectionMap.put(section, content);
				}
			} else if (content != null) {
				content.append(line.trim());
				content.append('\n');
			} else {
				throw new IOException("Inhalt ohne Section Marker in Link-Message-Datei");
			}
			line = reader.readLine();
		}
		final Map<Section, String> resultMap = new HashMap<Section, String>();
		for (final Section name : sectionMap.keySet()) {
			resultMap.put(name, sectionMap.get(name).toString());
		}
		return resultMap;
	}
	
	protected Properties parsePropertiesSection(final String section) throws ParseException {
		final Properties properties = new Properties();
		try {
			properties.load(new StringReader(section));
		} catch (IOException e) {
			throw new ParseException("Fehler beim Lesen der Properties-Section " + section, e);
		}
		return properties;
	}
	
	protected interface Parser<T> {
		T parse(String value) throws ParseException;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> Class<T>[] parseArrayProperty(final String value, final Parser<Class<T>> parser) throws ParseException {
		if (value == null) {
			return null;
		}
		final List<Class<T>> objList = new ArrayList<Class<T>>();
		final String[] values = value.split("\\s*,\\s*");
		for (final String aValue : values) {
			objList.add(parser.parse(aValue));
		}
		return objList.toArray(new Class[objList.size()]);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> Class<T> parseClassProperty(final Class<T> component, final String value) throws ParseException {
		if (value == null) {
			throw new ParseException("Ungueltiger Parameter in Setup-Section");
		}
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			final Class<?> clazz = cl.loadClass(value);
//			if (!component.equals(clazz)) {
//				throw new ParseException("Ungueltiger Typ " + value + " fuer Komponente " + component.getName());
//			}
			return (Class<T>) clazz;
		} catch (ClassNotFoundException e) {
			throw new ParseException("Kann Komponente " + value + " aus der Setup-Section nicht finden", e);
		}
	}
	
	protected LinkMessage parseLinkMessage(final String headerSection, final String bodySection)
			throws ParseException {
		if (headerSection == null) {
			throw new ParseException("No Request-Header-Section found in testcase");
		}
		if (bodySection == null) {
			throw new ParseException("No Request-Body-Section found in testcase");
		}
		final Properties headerParams = parsePropertiesSection(headerSection);
		final LinkMessage linkMessage = new LinkMessage();
		linkMessage.setApplikation(headerParams.getProperty(HeaderParam.APPLICATION));
		linkMessage.setFunktion(headerParams.getProperty(HeaderParam.FUNCTION));
		linkMessage.setAktion(headerParams.getProperty(HeaderParam.ACTION));
		linkMessage.setCorrelationId(headerParams.getProperty(HeaderParam.CORRELATION_ID));
		linkMessage.setInhalt(bodySection);
		return linkMessage;
	}
	
}
