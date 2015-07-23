/**
 *  Copyright (c) 2015 Innoave.com
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
package com.innoave.menura.link.expressions;

import java.util.Map;

import org.stringtemplate.v4.ST;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkExpressionRenderer {
	
	protected static final char DEFAULT_START_DELIMITER = '$';
	protected static final char DEFAULT_STOP_DELIMITER = '$';
	
	private char startDelimiter = DEFAULT_START_DELIMITER;
	private char stopDelimiter = DEFAULT_STOP_DELIMITER;

	
	public String render(final String template, final Map<String, Object> attributes) {
		final ST engine = new ST(template, startDelimiter, stopDelimiter);
		for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
			engine.add(entry.getKey(), entry.getValue());
		}
		return engine.render();
	}

	public char getStartDelimiter() {
		return startDelimiter;
	}

	public void setStartDelimiter(char startDelimiter) {
		this.startDelimiter = startDelimiter;
	}

	public char getStopDelimiter() {
		return stopDelimiter;
	}

	public void setStopDelimiter(char stopDelimiter) {
		this.stopDelimiter = stopDelimiter;
	}
	
}
