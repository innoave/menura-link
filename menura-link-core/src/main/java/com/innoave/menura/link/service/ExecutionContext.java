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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.NamingStrategie;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.api.TestStep;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class ExecutionContext {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final Map<String, SystemAdapter> stepNameSystemAdapterMap =
			new HashMap<String, SystemAdapter>();
	
	private final Map<String, LinkMessageHandler> stepNameActionHandlerMap =
			new HashMap<String, LinkMessageHandler>();

	private final Map<String, LinkMessageHandler> stepNameNavigationHandlerMap =
			new HashMap<String, LinkMessageHandler>();

	private final Map<String, LinkMessageHandler> stepNameVerificationHandlerMap =
			new HashMap<String, LinkMessageHandler>();
	
	private final Map<String, List<TestStep>> testListStepListMap =
			new HashMap<String, List<TestStep>>();

	private final Map<Class<SystemAdapter>, SystemAdapter> systemAdapterInstanceMap =
			Collections.synchronizedMap(new HashMap<Class<SystemAdapter>, SystemAdapter>());
	
	private final Map<String, String> correlationIdStepNameMap = 
			Collections.synchronizedMap(new HashMap<String, String>());
	
	private LinkConfiguration configuration = new LinkConfiguration();
	
	private NamingStrategie namingStrategie = new DefaultNamingStrategie();

	
	private static final ExecutionContext instance = new ExecutionContext();
	
	public static final ExecutionContext instance() {
		return instance;
	}
	
	private ExecutionContext() {
	}
	
	public Collection<SystemAdapter> getAllRegisteredSystemAdapter() {
		return Collections.unmodifiableCollection(stepNameSystemAdapterMap.values());
	}
	
	public SystemAdapter getSystemAdapter(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		return stepNameSystemAdapterMap.get(stepName);
	}
	
	public void registerSystemAdapter(final String stepName, final SystemAdapter systemAdapter) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		if (systemAdapter == null) {
			throw new IllegalArgumentException("SystemAdapter must not be null");
		}
		stepNameSystemAdapterMap.put(stepName, systemAdapter);
	}
	
	public void removeSystemAdapter(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		stepNameSystemAdapterMap.remove(stepName);
	}
	
	public LinkMessageHandler getActionHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		return stepNameActionHandlerMap.get(stepName);
	}
	
	public void registerActionHandler(final String stepName, final LinkMessageHandler actionHandler) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		if (actionHandler == null) {
			throw new IllegalArgumentException("ActionHandler must not be null");
		}
		stepNameActionHandlerMap.put(stepName, actionHandler);
	}
	
	public void removeActionHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		stepNameActionHandlerMap.remove(stepName);
	}
	
	public LinkMessageHandler getNavigationHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		return stepNameNavigationHandlerMap.get(stepName);
	}
	
	public void registerNavigationHandler(final String stepName, final LinkMessageHandler navigationHandler) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		if (navigationHandler == null) {
			throw new IllegalArgumentException("NavigationHandler must not be null");
		}
		stepNameNavigationHandlerMap.put(stepName, navigationHandler);
	}
	
	public void removeNavigationHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		stepNameNavigationHandlerMap.remove(stepName);
	}
	
	public LinkMessageHandler getVerificationHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		return stepNameVerificationHandlerMap.get(stepName);
	}
	
	public void registerVerificationHandler(final String stepName, final LinkMessageHandler verificationHandler) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		if (verificationHandler == null) {
			throw new IllegalArgumentException("VerificationHandler must not be null");
		}
		stepNameVerificationHandlerMap.put(stepName, verificationHandler);
	}
	
	public void removeVerificationHandler(final String stepName) {
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		stepNameVerificationHandlerMap.remove(stepName);
	}
	
	public List<TestStep> getTestStepList(final String listName) {
		if (listName == null) {
			throw new IllegalArgumentException("ListName must not be null");
		}
		List<TestStep> stepList = testListStepListMap.get(listName);
		if (stepList == null) {
			stepList = new ArrayList<TestStep>();
			testListStepListMap.put(listName, stepList);
		}
		return stepList;
	}
	
	public SystemAdapter getSystemAdapter(final Class<SystemAdapter> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type must not be null");
		}
		return systemAdapterInstanceMap.get(type);
	}
	
	public SystemAdapter provideSystemAdapter(
			final Class<SystemAdapter> systemAdapterClazz
			) throws LinkException {
		SystemAdapter systemAdapter;
		systemAdapter = systemAdapterInstanceMap.get(systemAdapterClazz);
		if (systemAdapter == null) {
			try {
				systemAdapter = systemAdapterClazz.newInstance();
				systemAdapterInstanceMap.put(systemAdapterClazz, systemAdapter);
			} catch (InstantiationException e) {
				final String errortext = "Kann SystemAdapter " + systemAdapterClazz.getName() + " nicht instanzieren";
				log.error(errortext);
				throw new LinkException(errortext, e);
			} catch (IllegalAccessException e) {
				final String errortext = "Kein Zugriff auf SystemAdapter " + systemAdapterClazz.getName();
				log.error(errortext);
				throw new LinkException(errortext, e);
			}
		}
		return systemAdapter;
	}
	
	public LinkMessageHandler provideMessageHandler(
			final Class<LinkMessageHandler> messageHandlerClazz
			) throws LinkException {
		final LinkMessageHandler messageHandler;
		try {
			messageHandler = messageHandlerClazz.newInstance();
		} catch (InstantiationException e) {
			final String errortext = "Kann LinkMesssageHandler " + messageHandlerClazz.getName() + " nicht instanzieren";
			log.error(errortext);
			throw new LinkException(errortext, e);
		} catch (IllegalAccessException e) {
			final String errortext = "Kein Zugriff auf LinkMessageHandler " + messageHandlerClazz.getName();
			log.error(errortext);
			throw new LinkException(errortext, e);
		}
		return messageHandler;
	}
	
	public String getStepNameForCorrelationId(final String correlationId) {
		if (correlationId == null) {
			throw new IllegalArgumentException("CorrelationId must not be null");
		}
		return correlationIdStepNameMap.get(correlationId);
	}
	
	public void registerStepNameForCorrelationId(final String correlationId, final String stepName) {
		if (correlationId == null) {
			throw new IllegalArgumentException("CorrelationId must not be null");
		}
		if (stepName == null) {
			throw new IllegalArgumentException("StepName must not be null");
		}
		correlationIdStepNameMap.put(correlationId, stepName);
	}
	
	public void removeStepNameForCorrelationId(final String correlationId) {
		if (correlationId == null) {
			throw new IllegalArgumentException("CorrelationId must not be null");
		}
		correlationIdStepNameMap.remove(correlationId);
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setConfigurationProperties(final Properties properties) {
		configuration.setAllProperties(properties);
	}
	
	public NamingStrategie getNamingStrategie() {
		return namingStrategie;
	}
	
	public void setNamingStrategie(final NamingStrategie namingStrategie) {
		this.namingStrategie = namingStrategie;
	}
	
}
