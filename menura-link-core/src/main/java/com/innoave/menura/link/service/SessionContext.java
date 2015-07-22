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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class SessionContext {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	
	private static final ThreadLocal<SessionContext> instance = new ThreadLocal<SessionContext>() {
		@Override
		protected SessionContext initialValue() {
			return new SessionContext();
		}
	};
	
	public static final SessionContext instance() {
		return instance.get();
	}
	
	private SessionContext() {
	}
	
	public Object getAttribute(final String name) {
		return attributes.get(name);
	}
	
	public void putAttribute(final String name, final Object value) {
		attributes.put(name, value);
	}
	
}
