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

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.ObjectLogger;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class ObjectLoggerFactory {
	
	private static final Map<Class<?>, Class<? extends ObjectLogger<?>>> objectLoggerTypeMap =
			new HashMap<Class<?>, Class<? extends ObjectLogger<?>>>();
	
	static {
		objectLoggerTypeMap.put(LinkMessage.class, LinkMessageLogger.class);
	}
	

	public static final <T> ObjectLogger<T> getObjectLogger(final Class<T> objectType) {
		final Class<? extends ObjectLogger<?>> loggerClazz = objectLoggerTypeMap.get(objectType);
		if (loggerClazz == null) {
			throw new IllegalStateException("No ObjectLogger class for object type " + objectType.getName() + " found");
		}
		try {
			@SuppressWarnings("unchecked")
			final ObjectLogger<T> instance = (ObjectLogger<T>) loggerClazz.newInstance();
			return instance;
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
