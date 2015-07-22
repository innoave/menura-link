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

import java.util.Map;
import java.util.Properties;

import com.innoave.menura.link.api.Configuration;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkConfiguration implements Configuration {
	
	private Properties properties = new Properties();
	
	public void resetToDefaults() {
		properties.clear();
	}
	
	public void setAllProperties(final Properties properties) {
		for (final String name : properties.stringPropertyNames()) {
			this.properties.put(name, properties.get(name));
		}
	}
	
	public void setAllProperties(final Map<String, String> properties) {
		this.properties.putAll(properties);
	}
	
	public void setProperty(final String key, final String value) {
		properties.put(key, value);
	}
	
	@Override
	public String get(final Key key) {
		String value;
		value = properties.getProperty(key.getPropertyKey());
		if (value == null) {
			value = key.getDefaultValue();
		}
		return value;
	}
	
	public void set(final Key key, final String value) {
		properties.put(key.getPropertyKey(), value);
	}
	
}
