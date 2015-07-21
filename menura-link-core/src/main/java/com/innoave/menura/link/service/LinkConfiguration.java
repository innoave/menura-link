/**
 * @(#) $Id: LinkConfiguration.java 9314 2014-10-13 16:41:20Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: LinkConfiguration.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import java.util.Map;
import java.util.Properties;

import com.innoave.menura.link.api.Configuration;

/**
 *
 *
 * @version $Revision: 9314 $
 * @author haraldmaida
 * @author $Author: maida $
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
