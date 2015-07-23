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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class SessionContext {
	
	private final String sessionId;
	
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	
	private final Map<String, Integer> counters = new HashMap<String, Integer>();
	
	
	private static final AtomicInteger sessionIdCounter = new AtomicInteger();
	
	private static final ThreadLocal<SessionContext> instance = new ThreadLocal<SessionContext>() {
		@Override
		protected SessionContext initialValue() {
			final int sessionId = sessionIdCounter.getAndIncrement();
			return new SessionContext(String.valueOf(sessionId));
		}
	};
	
	public static final SessionContext instance() {
		return instance.get();
	}

	private SessionContext(final String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * Returns all attributes currently set in this context.
	 * 
	 * <p>The returned Map contains all attributes currently set in this context. Attributes
	 * that are later put to this context or later removed from this context will not effect
	 * the returned Map of attributes. Further any modification of the returned Map has no 
	 * impact on the attributes defined in this context.
	 * 
	 * @return a Map of key/value pairs of all attributes currently set in this context.
	 */
	public Map<String, Object> getAllCurrentAttributes() {
		return new HashMap<String, Object>(attributes);
	}
	
	public Object getAttribute(final String name) {
		return attributes.get(name);
	}
	
	public void putAttribute(final String name, final Object value) {
		attributes.put(name, value);
	}
	
	public void removeAttribute(final String name) {
		attributes.remove(name);
	}
	
	public int incrementCounter(final String name) {
		Integer counter = counters.get(name);
		if (counter == null) {
			counter = 1;
		} else {
			counter++;
		}
		counters.put(name, counter);
		return counter;
	}
	
	public int getCounterValue(final String name) {
		Integer counter = counters.get(name);
		if (counter == null) {
			counter = 0;
			counters.put(name, counter);
		}
		return counter;
	}
	
}
