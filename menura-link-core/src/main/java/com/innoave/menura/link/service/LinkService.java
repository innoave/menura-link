/**
 * @(#) $Id: LinkService.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: LinkService.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
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
import com.innoave.menura.link.api.TestStepExecutor;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
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
		log.debug("Lese TestStep {}", file.getPath());
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
	
	public void executeRegisteredTestSteps(final String testListName) throws LinkException {
		final Collection<TestStep> testStepList = context.getTestStepList(testListName);
		executeTestSteps(testStepList);
	}
	
	public void executeTestSteps(final TestStep... testSteps) throws LinkException {
		for (final TestStep testStep : testSteps) {
			executeTestStep(testStep);
		}
	}	
	public void executeTestSteps(final Collection<TestStep> testStepList) throws LinkException {
		for (final TestStep testStep : testStepList) {
			executeTestStep(testStep);
		}
	}
	
	public void executeTestStep(final TestStep testStep) throws LinkException {
		//Create test step executor
		final String stepName = testStep.getName();
		final FunctionalType type = testStep.getType();
		if (type == null) {
			throw new LinkException("Unknown functional type of test step");
		}
		LinkMessageHandler handler = null;
		switch (type) {
		case ACTION:
			handler = context.getActionHandler(stepName);
			registerStepNameForTestStep(testStep);
			break;
		case VERIFICATION:
			//Verification Step is not executed proactively
//			handler = context.getVerificationHandler(stepName);
			break;
		case NAVIGATION:
			handler = context.getNavigationHandler(stepName);
			registerStepNameForTestStep(testStep);
			break;
		default:
			throw new LinkException("Unsupported functional type of test step");
		}
		if (handler != null) {
			final SystemAdapter systemAdapter = context.getSystemAdapter(stepName);
			final LinkMessage message = testStep.getMessage();
			final TestStepExecutor stepExecutor = new DefaultTestStepExecutor(
					stepName, message, handler, systemAdapter);
			//Do it!
			stepExecutor.execute();
		}
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
