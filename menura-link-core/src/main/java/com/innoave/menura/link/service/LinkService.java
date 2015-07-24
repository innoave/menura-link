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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.FunctionalType;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.NamingStrategie;
import com.innoave.menura.link.api.ParseException;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.api.TestStep;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ExecutionContext context = ExecutionContext.instance();
	
	
	public void loadConfiguration(final File file) throws IOException {
		final InputStream in = new FileInputStream(file);
		loadConfiguration(in);
	}
	
	public void loadConfiguration(final InputStream in) throws IOException {
		try {
			final Properties loaded = new Properties();
			loaded.load(in);
			context.setConfigurationProperties(loaded);
		} finally {
			in.close();
		}
	}
	
	public TestStep readTestStep(final File file) throws IOException, ParseException {
		log.debug("Lese TestStep {}", file.getAbsolutePath());
		final DefaultTestStepReader testStepReader = new DefaultTestStepReader();
		return testStepReader.readTestStep(file);
	}
	
	public TestStep readTestStep(final String text) throws IOException, ParseException {
		final DefaultTestStepReader testStepReader = new DefaultTestStepReader();
		return testStepReader.readTestStep(text);
	}
	
	public void registerTestStep(final String testListName, final TestStep testStep) throws LinkException {
		final Configuration configuration = context.getConfiguration();
		final NamingStrategie namingStrategie = context.getNamingStrategie();
		final String stepName = testStep.getName();
		final FunctionalType stepType = testStep.getType();
		if (stepType == null) {
			throw new IllegalStateException("TestStep has no FunctionalType specified");
		}
		final Class<SystemAdapter> systemAdapterClazz = testStep.getSystemAdapter();
		SystemAdapter systemAdapter = context.getSystemAdapter(systemAdapterClazz);
		if (systemAdapter == null) {
			systemAdapter = context.provideSystemAdapter(systemAdapterClazz);
			systemAdapter.setName(namingStrategie.forComponent(systemAdapterClazz)
					.withStepName(stepName));
			systemAdapter.startup(configuration);
		}
		context.registerSystemAdapter(stepName, systemAdapter);
		final Class<LinkMessageHandler> messageHandlerClazz = testStep.getMessageHandler();
		final LinkMessageHandler messageHandler = context.provideMessageHandler(messageHandlerClazz);
		messageHandler.setName(namingStrategie.forComponent(messageHandlerClazz)
				.withStepName(stepName));
		messageHandler.startup(configuration, testStep);
		switch(stepType) {
		case ACTION:
			context.registerActionHandler(stepName, messageHandler);
			break;
		case VERIFICATION:
			context.registerVerificationHandler(stepName, messageHandler);
			break;
		case NAVIGATION:
			context.registerNavigationHandler(stepName, messageHandler);
			break;
		}
		context.getTestStepList(testListName).add(testStep);
	}
	
	public void removeTestStep(final String testListName, final TestStep testStep) {
		final String stepName = testStep.getName();
		context.getTestStepList(testListName).remove(testStep);
		final FunctionalType type = testStep.getType();
		if (type != null) {
			switch (type) {
			case ACTION:
				context.removeActionHandler(stepName);
				break;
			case VERIFICATION:
				context.removeVerificationHandler(stepName);
				break;
			case NAVIGATION:
				context.removeNavigationHandler(stepName);
				break;
			}
		}
		context.removeSystemAdapter(stepName);
	}
	
	public void startExecutionOfRegisteredTestSteps(final String testListName, final SessionContext session) throws LinkException {
		final Collection<TestStep> testStepList = context.getTestStepList(testListName);
		startExecutionOfTestSteps(session, testStepList);
	}
	
	public void startExecutionOfTestSteps(final SessionContext session, final TestStep... testSteps) throws LinkException {
		for (final TestStep testStep : testSteps) {
			startTestStepExecution(testStep, session);
		}
	}
	
	public void startExecutionOfTestSteps(final SessionContext session, final Collection<TestStep> testStepList) throws LinkException {
		for (final TestStep testStep : testStepList) {
			startTestStepExecution(testStep, session);
		}
	}
	
	public void startTestStepExecution(final TestStep testStep, final SessionContext session) throws LinkException {
		//Create test step executor
		final String stepName = testStep.getName();
		final SystemAdapter systemAdapter = context.getSystemAdapter(stepName);
		final LinkMessage message = testStep.getMessage();
		final FunctionalType type = testStep.getType();
		if (type == null) {
			throw new LinkException("Unknown functional type of test step");
		}
		registerStepNameForTestStep(testStep);
		final TestStepExecutor stepExecutor;
		switch (type) {
		case ACTION: {
			final LinkMessageHandler handler;
			handler = context.getActionHandler(stepName);
			stepExecutor = new ActionTestStepExecutor(
					stepName, message, handler, systemAdapter);
			log.info("Starting with action test step '{}'", stepName);
		} break;
		case VERIFICATION: {
			final LinkMessageHandler handler;
			handler = context.getVerificationHandler(stepName);
			stepExecutor = new VerificationTestStepExecutor(
					stepName, message, handler, systemAdapter);
			log.info("Starting with verification test step '{}'", stepName);
		} break;
		case NAVIGATION: {
			final LinkMessageHandler handler;
			handler = context.getNavigationHandler(stepName);
			stepExecutor = new ActionTestStepExecutor(
					stepName, message, handler, systemAdapter);
			log.info("Starting with navigation test step '{}'", stepName);
		} break;
		default:
			throw new LinkException("Unsupported functional type of test step");
		}
		//Do it!
		stepExecutor.execute(session);
	}
	
	protected void registerStepNameForTestStep(final TestStep testStep) {
		final String stepName = testStep.getName();
		final LinkMessage message = testStep.getMessage();
		if (message == null) {
			log.warn("TestStep without Message: TestStep.name={}", stepName);
			return;
		}
		final String correlationId = message.getCorrelationId();
		if (correlationId == null) {
			log.warn("TestStep.message without correlationId: TestStep.name={}, Message={}",
					stepName, message.getClass().getSimpleName());
			return;
		}
		context.registerStepNameForCorrelationId(correlationId, stepName);
	}
	
	public void shutdown() {
		for (final SystemAdapter systemAdapter : context.getAllRegisteredSystemAdapter()) {
			try {
				systemAdapter.shutdown();
			} catch (LinkException e) {
				log.warn("Error on shutting down SystemAdapter {}", systemAdapter.getName());
			}
		}
	}
	
}
